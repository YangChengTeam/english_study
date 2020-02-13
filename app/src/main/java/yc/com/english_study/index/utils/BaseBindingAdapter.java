package yc.com.english_study.index.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by wanglin  on 2019/4/20 09:48.
 */
public abstract class BaseBindingAdapter<T, VM extends ViewDataBinding, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {


    public BaseBindingAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public BaseBindingAdapter(List<T> data) {
        super(data);
    }

    public BaseBindingAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != LOADING_VIEW && viewType != HEADER_VIEW && viewType != EMPTY_VIEW && viewType != FOOTER_VIEW) {
            VM d = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), this.mLayoutResId, parent, false);
            BaseBindingHolder mvViewHolder = new BaseBindingHolder<>(d);
            bindViewClickListener(mvViewHolder);
            mvViewHolder.setBindingAdapter(this);
            return (K) mvViewHolder;
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    private void bindViewClickListener(final BaseViewHolder baseViewHolder) {
        if (baseViewHolder == null) {
            return;
        }
        final View view = baseViewHolder.itemView;
        if (view == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnItemClickListener().onItemClick(BaseBindingAdapter.this, v, baseViewHolder.getLayoutPosition() - getHeaderLayoutCount());

                }
            });
        }
        if (getOnItemLongClickListener() != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    getOnItemLongClickListener().onItemLongClick(BaseBindingAdapter.this, v, baseViewHolder.getLayoutPosition() - getHeaderLayoutCount());
                    return false;
                }
            });
        }
    }


    /**
     * if you want to use subclass of BaseViewHolder in the adapter,
     * you must override the method to create new ViewHolder.
     *
     * @param view view
     * @return new ViewHolder
     */
    protected K createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        K k = null;
        if (z != null) {
            k = createGenericKInstance(z, view);
        }
        return null != k ? k : (K) new BaseViewHolder(view);
    }

    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view
     * @return
     */
    private K createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            String buffer = Modifier.toString(z.getModifiers());
            String className = z.getName();
            // inner and unstatic class
            if (className.contains("$") && !buffer.contains("static")) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                return (K) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                return (K) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }

}
