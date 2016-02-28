package org.slevin.dao.service;


import org.slevin.common.EmotionImage;
import org.slevin.dao.EmotionImageDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class EmotionImageService extends EntityService<EmotionImage> implements EmotionImageDao {

	
}

