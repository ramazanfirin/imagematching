package org.slevin.dao.service;


import org.slevin.common.Image;
import org.slevin.dao.ImageDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class ImageService extends EntityService<Image> implements ImageDao {

	
}

