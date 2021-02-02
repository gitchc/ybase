package com.yonyou.mde.bigCube.main;

import com.yonyou.mde.bigCube.interfaces.IServer;
import com.yonyou.mde.context.MdeContext;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.MultiDimModel;
import com.yonyou.mde.model.api.MultiDimModelApi;

public class Server implements IServer {
    private static MdeContext mdeContext = MdeContext.getInstance();
    private static Server server;
    public static Server getServer() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public static Cube getCube(String cubeName) throws MdeException {
        MultiDimModelApi content = mdeContext.getModelApi(cubeName);
        MultiDimModel model = mdeContext.getModel(cubeName);
        return new Cube(content, model);
    }
}
