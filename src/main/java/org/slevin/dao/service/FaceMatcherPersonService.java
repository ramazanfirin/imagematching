package org.slevin.dao.service;


import org.slevin.common.AyonixPerson;
import org.slevin.common.FaceMatcherPerson;
import org.slevin.common.Image;
import org.slevin.dao.AyonixPersonDao;
import org.slevin.dao.FaceMatcherPersonDao;
import org.slevin.dao.ImageDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class FaceMatcherPersonService extends EntityService<FaceMatcherPerson> implements FaceMatcherPersonDao {

	
}

