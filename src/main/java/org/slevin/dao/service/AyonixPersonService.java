package org.slevin.dao.service;


import org.slevin.common.AyonixPerson;
import org.slevin.common.Image;
import org.slevin.dao.AyonixPersonDao;
import org.slevin.dao.ImageDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class AyonixPersonService extends EntityService<AyonixPerson> implements AyonixPersonDao {

	
}

