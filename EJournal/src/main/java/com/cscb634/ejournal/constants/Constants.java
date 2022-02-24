package com.cscb634.ejournal.constants;

public final class Constants {

    private Constants() {
    }

    public static final String API_VERSION = "/v1";
    public static final String API_PREFIX = "/api";
    public static final String API_BASE = API_PREFIX + API_VERSION;

    public static final String SCHOOL_API =  API_BASE + "/school";
    public static final String DIRECTOR_API = API_BASE + "/director";
    public static final String TEACHER_API =  API_BASE + "/teacher";
    public static final String STUDENT_API = API_BASE + "/student";
    public static final String PARENT_API = API_BASE + "/parent";
    public static final String CURRICULUM_API = API_BASE + "/curriculum";
    public static final String LOGIN_API = API_BASE + "/login";

}
