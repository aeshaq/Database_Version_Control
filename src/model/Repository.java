package model;

import java.util.Date;

public class Repository {

    private final String Repository_name;
    private final String Organization_name;
    private final Date date;

    public Repository(String name, String organization_name, Date date) {
        Repository_name = name;
        this.Organization_name = organization_name;
        this.date = date;
    }

    public String getRepository_name() {
        return this.Repository_name;
    }

    public String getOrganization_name() {
        return Organization_name;
    }

    public Date getDate() {
        return date;
    }

}