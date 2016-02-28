package org.slevin.dao;

import org.slevin.common.EmotionImage;

public interface EmotionImageDao extends EntityDao<EmotionImage>{
	
	public  String sendFile(byte[] data);
}
