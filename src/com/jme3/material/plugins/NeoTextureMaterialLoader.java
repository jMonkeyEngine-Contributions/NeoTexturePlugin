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

/**
 *
 * @author normenhansen
 */
public class NeoTextureMaterialLoader implements AssetLoader {

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
        
        // setup texture generator
        TextureGenerator.setUseCache(true);
        TextureGenerator.loadGraph(assetInfo.openStream());

        for (String n : TextureGenerator.getTextureNames()) {
            // create the texture into an int[]
            int[] data = TextureGenerator.generateTexture_ARGB(n, res, res);

            // flip the components the way AWT likes them
            for (int i = 0; i < res * res * 4; i += 4) {
                byte r = (byte) data[i + 0];
                byte g = (byte) data[i + 1];
                byte b = (byte) data[i + 2];
                byte a = (byte) data[i + 3];
                data[i + 0] = b;
                data[i + 1] = g;
                data[i + 2] = r;
                data[i + 3] = a;
            }

            ByteBuffer buffer = BufferUtils.createByteBuffer();//data);
            Image image = new Image(Image.Format.ABGR8, res, res, buffer);
            Texture2D texture = new Texture2D(image);
            mat.setTexture(n, texture);
        }

        TextureGenerator.clearCache();

        return mat;
    }
}