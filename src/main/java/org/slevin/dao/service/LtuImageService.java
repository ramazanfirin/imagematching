package org.slevin.dao.service;


import org.slevin.common.LtuImage;
import org.slevin.dao.LtuImageDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class LtuImageService extends EntityService<LtuImage> implements LtuImageDao {

	
}

