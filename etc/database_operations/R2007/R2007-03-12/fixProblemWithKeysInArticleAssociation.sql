delete FROM ARTICLE_ASSOCIATION WHERE KEY_ARTICLE NOT IN (SELECT ID_INTERNAL FROM RESULT);