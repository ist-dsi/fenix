select concat('update GENERIC_EVENT set START_TIME_DATE_HOUR_MINUTE_SECOND = "' , START_TIME_DATE_HOUR_MINUTE_SECOND , '", END_TIME_DATE_HOUR_MINUTE_SECOND = "' , END_TIME_DATE_HOUR_MINUTE_SECOND , '", BEGIN_DATE = "' , BEGIN , '", END_DATE = "' , END , '", DAILY_FREQUENCY_MARK_SUNDAY = "' , COALESCE(DAILY_FREQUENCY_MARK_SUNDAY,1) , '", DAILY_FREQUENCY_MARK_SATURDAY = "' , COALESCE(DAILY_FREQUENCY_MARK_SATURDAY,1) ,'" where ID_INTERNAL = ' , KEY_GENERIC_EVENT , ';') as "" from RESOURCE_ALLOCATION where KEY_GENERIC_EVENT is not null and OJB_CONCRETE_CLASS like "%GenericEventSpaceOccupation%";