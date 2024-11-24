package com.bcht.rminf.modules.hkpostgresql;

import com.bcht.rminf.modules.hkpostgresql.model.DsWeather;
import com.bcht.rminf.modules.hkpostgresql.model.WeatherInfo;
import org.noear.solon.core.convert.Converter;
import org.noear.solon.core.exception.ConvertException;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class HkWeatherConvert implements Converter<WeatherInfo, DsWeather> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public DsWeather convert(WeatherInfo value) throws ConvertException {
        DsWeather dsWeather = new DsWeather();
        dsWeather.setLsh(UUID.randomUUID().toString().replace("-", ""));
        dsWeather.setSbxh(value.id());
        dsWeather.setCjsj(value.obsTime().format(formatter));
        dsWeather.setHjwd(value.temp());
        dsWeather.setLmwd(value.feelsLike());
        dsWeather.setSjfx(value.wind360());
        dsWeather.setXdfx(value.windDir());
        dsWeather.setFsssz(String.valueOf(Double.parseDouble(value.windSpeed()) / 3.6));
        dsWeather.setHjsd(value.humidity());
        dsWeather.setQy(value.pressure());
        dsWeather.setNjdpjz(value.vis());
        dsWeather.setYljgljz(value.precip());
        return dsWeather;
    }

}
