/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.material.plugins;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import com.jme3.material.Material;
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
        if (assetInfo.getKey() instanceof NeoTextureMaterialKey) {
            key = (NeoTextureMaterialKey) assetInfo.getKey();
            res = key.getResolution();
            matDef = key.getMaterialDef();
        }

        Material mat = new Material(assetInfo.getManager(), matDef);

        synchronized (neoLock) {
            // setup texture generator
            TextureGenerator.setUseCache(true);
            TextureGenerator.loadGraph(assetInfo.openStream());

            for (String n : TextureGenerator.getTextureNames()) {
                Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Found neo texture {0}", n);
                // create the texture into an int[]
                int[] data = TextureGenerator.generateTexture_ARGB(n, res, res);

                ByteBuffer buffer = BufferUtils.createByteBuffer(data.length * 4);
                buffer.asIntBuffer().put(data).clear();

//                // flip the components the way AWT likes them
//                for (int i = 0; i < res * res * 4; i+=4){
//                    byte a = buffer.get(i+0);
//                    byte r = buffer.get(i+1);
//                    byte g = buffer.get(i+2);
//                    byte b = buffer.get(i+3);
//
//                    buffer.put(i+0, r);
//                    buffer.put(i+1, g);
//                    buffer.put(i+2, b);
//                    buffer.put(i+3, a);
//                }
//                buffer.clear();

                Image image = new Image(Image.Format.RGBA8, res, res, buffer);
                Texture2D texture = new Texture2D(image);
                mat.setTexture(n, texture);
            }

            TextureGenerator.clearCache();
        }
        return mat;
    }
}
