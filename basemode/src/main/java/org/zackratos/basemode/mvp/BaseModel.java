package org.zackratos.basemode.mvp;


/**
 * @Date 2017/9/1
 * @author leaf
 * @version 1.0
 * @Note
 */
public abstract  class BaseModel<SubP>  {

    protected SubP mPresenter;

    public BaseModel(SubP presenter) {
        this.mPresenter = presenter;
    }
}
