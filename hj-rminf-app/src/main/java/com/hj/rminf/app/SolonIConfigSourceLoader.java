package com.hj.rminf.app;

import io.nop.commons.util.objects.ValueWithLocation;
import io.nop.config.source.IConfigSource;
import io.nop.config.source.IConfigSourceLoader;
import org.noear.solon.Solon;
import org.noear.solon.core.convert.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyl
 * @date 2024年08月05日 23:15
 */
public class SolonIConfigSourceLoader implements IConfigSourceLoader {

    @Override
    public IConfigSource loadConfigSource(IConfigSource iConfigSource) {
        return new IConfigSource() {
            @Override
            public String getName() {
                return "solon-cfg";
            }

            @Override
            public Map<String, ValueWithLocation> getConfigValues() {
                Map<String, ValueWithLocation> map = new HashMap<>();
                Solon.cfg().entrySet().forEach(entry -> {
                    map.put((String) entry.getKey(), ValueWithLocation.of(null, entry.getValue()));
                });
                return map;
            }

            @Override
            public ValueWithLocation getConfigValue(String name) {
                return IConfigSource.super.getConfigValue(name);
            }

            @Override
            public <T> T getConfigValue(String name, T defaultValue) {
                return Solon.cfg().getOrDefault(name, defaultValue, (value) -> {
                    Converter converter = Solon.app().converterManager().find(String.class, defaultValue.getClass());
                    if (converter == null) {
                        return null;
                    } else {
                        return (T) converter.convert(value);
                    }
                });
            }

            @Override
            public void addOnChange(Runnable runnable) {

            }

            @Override
            public void close() throws Exception {

            }
        };
    }

}
