select concat('update LESSON set KEY_PERIOD = ' , KEY_PERIOD , ' where ID_INTERNAL = ' , KEY_LESSON , ';') as "" from RESOURCE_ALLOCATION where KEY_LESSON is not null and OJB_CONCRETE_CLASS like "%LessonSpaceOccupation%";