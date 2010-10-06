/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.material.plugins;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import com.jme3.material.Material;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import com.jme3.util.BufferUtils;
import com.mystictri.neotexture.TextureGenerator;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author normenhansen
 */
public class NeoTextureMaterialLoader implements AssetLoader {

    public static final Object neoLock = new Object();

    public Object load(AssetInfo assetInfo) throws IOException {
        NeoTextureMaterialKey key = null;
        int res = 1024;
        String matDef = "Common/MatDefs/Light/Lighting.j3md";
        Texture2D.WrapMode mode = Texture2D.WrapMode.Repeat;
        boolean useCache = true;

        if (assetInfo.getKey() instanceof NeoTextureMaterialKey) {
            key = (NeoTextureMaterialKey) assetInfo.getKey();
            res = key.getResolution();
            matDef = key.getMaterialDef();
            mode = key.getWrapMode();
            useCache = key.isUseCache();
        }

        Material mat = new Material(assetInfo.getManager(), matDef);

        synchronized (neoLock) {
            // setup texture generator
            TextureGenerator.setUseCache(useCache);
            TextureGenerator.loadGraph(assetInfo.openStream());

            for (String n : TextureGenerator.getTextureNames()) {
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Found neo texture {0}", n);
                // create the texture into an int[]
                int[] data = TextureGenerator.generateTexture_ABGR(n, res, res);

                ByteBuffer buffer = BufferUtils.createByteBuffer(data.length * 4);
                buffer.asIntBuffer().put(data).clear();

                Image image = new Image(Image.Format.RGBA8, res, res, buffer);
                Texture2D texture = new Texture2D(image);
                texture.setWrap(mode);
                mat.setTexture(n, texture);
            }

            if (useCache) {
                TextureGenerator.clearCache();
            }
        }
        return mat;
    }
}
