package op.beishi7.core.http.action;

import android.content.Context;

import op.beishi7.core.http.HttpConfiguration;

public abstract class BaseHttpAction implements IHttpAction {
    
    public abstract void init(Context context, HttpConfiguration configuration);
    
}
