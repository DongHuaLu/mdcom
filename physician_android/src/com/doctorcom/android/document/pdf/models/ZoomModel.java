package com.doctorcom.android.document.pdf.models;

import com.doctorcom.android.document.pdf.events.BringUpZoomControlsEvent;
import com.doctorcom.android.document.pdf.events.EventDispatcher;
import com.doctorcom.android.document.pdf.events.ZoomChangedEvent;
import com.doctorcom.android.document.pdf.events.ZoomListener;

public class ZoomModel extends EventDispatcher
{
    private float zoom = 1.0f;
    private static final float INCREMENT_DELTA = 0.05f;
    private boolean horizontalScrollEnabled;
    private boolean isCommited;

    public void setZoom(float zoom)
    {
        zoom = Math.max(zoom, 1.0f);
        if (this.zoom != zoom)
        {
            float oldZoom = this.zoom;
            this.zoom = zoom;
            isCommited = false;
            dispatch(new ZoomChangedEvent(zoom, oldZoom));
        }
    }

    public float getZoom()
    {
        return zoom;
    }

    public void increaseZoom()
    {
        setZoom(getZoom() + INCREMENT_DELTA);
    }

    public void decreaseZoom()
    {
        setZoom(getZoom() - INCREMENT_DELTA);
    }

    public void toggleZoomControls()
    {
        dispatch(new BringUpZoomControlsEvent());
    }

    public void setHorizontalScrollEnabled(boolean horizontalScrollEnabled)
    {
        this.horizontalScrollEnabled = horizontalScrollEnabled;
    }

    public boolean isHorizontalScrollEnabled()
    {
        return horizontalScrollEnabled;
    }

    public boolean canDecrement()
    {
        return zoom > 1.0f;
    }

    public void commit()
    {
        if (!isCommited)
        {
            isCommited = true;
            dispatch(new ZoomListener.CommitZoomEvent());
        }
    }
}
