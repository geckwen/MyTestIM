package net.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;

import net.factory.utils.DiffUiDataCallback;

/**
 * App基础BaseModel的基础类
 * Created by CLW on 2017/9/26.
 */

public abstract class BaseDbModel<Model> extends BaseModel implements DiffUiDataCallback.UiDataDiffer<Model> {

}
