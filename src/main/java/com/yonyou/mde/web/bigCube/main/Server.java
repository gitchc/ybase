package com.yonyou.mde.web.bigCube.main;

import com.yonyou.mde.api.MultiDimModelApi;
import com.yonyou.mde.context.MdeContext;
import com.yonyou.mde.model.MultiDimModel;
import com.yonyou.mde.web.bigCube.interfaces.IServer;

public class Server implements IServer {
    private static Server server;
    private MdeContext mdeContext;

    public Server(MdeContext mdeContext) {
        this.mdeContext = mdeContext;
    }

    public static Server getServer() {
        if (server == null) {
            server = new Server(MdeContext.get());
        }
        return server;
    }
    public Cube getCube(String cubeName){
        MultiDimModelApi content = mdeContext.getModelApi(cubeName);
        MultiDimModel model = mdeContext.getModel(cubeName);
        return new Cube(content,model);
    }
}
