package com.bcht.rminf.modules.hkpostgresql.model;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;

@Entity
public interface WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id();

    String cloud();

    java.time.LocalDateTime createTime();

    String dew();

    String feelsLike();

    String humidity();

    String icon();

    String location();

    java.time.LocalDateTime obsTime();

    String precip();

    String pressure();

    String temp();

    String text();

    String vis();

    String wind360();

    String windDir();

    String windScale();

    String windSpeed();

}
