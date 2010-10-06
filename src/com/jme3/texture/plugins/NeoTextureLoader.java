/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.texture.plugins;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import com.jme3.material.plugins.NeoTextureMaterialLoader;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import com.jme3.util.BufferUtils;
import com.mystictri.neotexture.TextureGenerator;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author normenhansen
 */
public class NeoTextureLoader implements AssetLoader {

    public Object load(AssetInfo assetInfo) throws IOException {
        NeoTextureKey key = null;
        int res = 1024;
        String textureName = "texture";
        boolean useCache = true;
        if (assetInfo.getKey() instanceof NeoTextureKey) {
            key = (NeoTextureKey) assetInfo.getKey();
            res = key.getResolution();
            textureName = key.getTextureName();
            useCache = key.isUseCache();
        }

        synchronized (NeoTextureMaterialLoader.neoLock) {
            // setup texture generator
            TextureGenerator.setUseCache(useCache);
            TextureGenerator.loadGraph(assetInfo.openStream());

            for (String n : TextureGenerator.getTextureNames()) {
                // create the texture into an int[]
                if (n.equals(textureName)) {
                    int[] data = TextureGenerator.generateTexture_ABGR(n, res, res);
                    ByteBuffer buffer = BufferUtils.createByteBuffer(data.length * 4);
                    buffer.asIntBuffer().put(data).clear();

                    // flip the components the way jME3 likes them
                    for (int i = 0; i < res * res * 4; i += 4) {
                        byte b = buffer.get(i + 0);
                        byte g = buffer.get(i + 1);
                        byte r = buffer.get(i + 2);
                        byte a = buffer.get(i + 3);

                        buffer.put(i + 0, r);//r
                        buffer.put(i + 1, g);//g
                        buffer.put(i + 2, b);//b
                        buffer.put(i + 3, a);
                    }
                    buffer.clear();

                    Image image = new Image(Image.Format.RGBA8, res, res, buffer);
                    Texture2D texture = new Texture2D(image);
                    TextureGenerator.clearCache();
                    return texture;
                }
            }
            if (useCache) {
                TextureGenerator.clearCache();
            }
        }
        return null;
    }
}
