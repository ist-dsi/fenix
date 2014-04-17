insert into RESOURCE_ALLOCATION (OJB_CONCRETE_CLASS, KEY_RESOURCE, START_TIME_DATE_HOUR_MINUTE_SECOND, END_TIME_DATE_HOUR_MINUTE_SECOND, DAY_OF_WEEK, KEY_PERIOD, WEEK_OF_QUINZENAL_START, KEY_WRITTEN_EVALUATION, KEY_LESSON, KEY_GENERIC_EVENT, DAILY_FREQUENCY_MARK_SUNDAY, DAILY_FREQUENCY_MARK_SATURDAY) select 'net.sourceforge.fenixedu.domain.space.GenericEventSpaceOccupation', KEY_ROOM, START_TIME_DATE_HOUR_MINUTE_SECOND, END_TIME_DATE_HOUR_MINUTE_SECOND, DAY_OF_WEEK, KEY_PERIOD, WEEK_OF_QUINZENAL_START, KEY_WRITTEN_EVALUATION, KEY_LESSON, KEY_GENERIC_EVENT, DAILY_FREQUENCY_MARK_SUNDAY, DAILY_FREQUENCY_MARK_SATURDAY from ROOM_OCCUPATION where KEY_GENERIC_EVENT is not null;
insert into RESOURCE_ALLOCATION (OJB_CONCRETE_CLASS, KEY_RESOURCE, START_TIME_DATE_HOUR_MINUTE_SECOND, END_TIME_DATE_HOUR_MINUTE_SECOND, DAY_OF_WEEK, KEY_PERIOD, WEEK_OF_QUINZENAL_START, KEY_WRITTEN_EVALUATION, KEY_LESSON, KEY_GENERIC_EVENT, DAILY_FREQUENCY_MARK_SUNDAY, DAILY_FREQUENCY_MARK_SATURDAY) select 'net.sourceforge.fenixedu.domain.space.WrittenEvaluationSpaceOccupation', KEY_ROOM, START_TIME_DATE_HOUR_MINUTE_SECOND, END_TIME_DATE_HOUR_MINUTE_SECOND, DAY_OF_WEEK, KEY_PERIOD, WEEK_OF_QUINZENAL_START, KEY_WRITTEN_EVALUATION, KEY_LESSON, KEY_GENERIC_EVENT, DAILY_FREQUENCY_MARK_SUNDAY, DAILY_FREQUENCY_MARK_SATURDAY from ROOM_OCCUPATION where KEY_WRITTEN_EVALUATION is not null;
insert into RESOURCE_ALLOCATION (OJB_CONCRETE_CLASS, KEY_RESOURCE, START_TIME_DATE_HOUR_MINUTE_SECOND, END_TIME_DATE_HOUR_MINUTE_SECOND, DAY_OF_WEEK, KEY_PERIOD, WEEK_OF_QUINZENAL_START, KEY_WRITTEN_EVALUATION, KEY_LESSON, KEY_GENERIC_EVENT, DAILY_FREQUENCY_MARK_SUNDAY, DAILY_FREQUENCY_MARK_SATURDAY) select 'net.sourceforge.fenixedu.domain.space.LessonSpaceOccupation', KEY_ROOM, START_TIME_DATE_HOUR_MINUTE_SECOND, END_TIME_DATE_HOUR_MINUTE_SECOND, DAY_OF_WEEK, KEY_PERIOD, WEEK_OF_QUINZENAL_START, KEY_WRITTEN_EVALUATION, KEY_LESSON, KEY_GENERIC_EVENT, DAILY_FREQUENCY_MARK_SUNDAY, DAILY_FREQUENCY_MARK_SATURDAY from ROOM_OCCUPATION where KEY_LESSON is not null;

drop table ROOM_OCCUPATION;