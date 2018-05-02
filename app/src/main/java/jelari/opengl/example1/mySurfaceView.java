package jelari.opengl.example1;

import android.content.Context;
import android.opengl.GLSurfaceView;

import jelari.opengl.example1.myGLRenderer;

class mySurfaceView extends GLSurfaceView
{
    private boolean setRendererFlag=false;

    myGLRenderer renderer;
    public mySurfaceView(Context context)
    {
        super(context);

        setEGLContextClientVersion(2);

        renderer = new myGLRenderer();
        setRenderer(renderer);
        setRendererFlag = true;
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }
    public myGLRenderer  getRenderer()
    {
        return renderer;
    }


}
