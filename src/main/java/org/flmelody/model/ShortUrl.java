package org.flmelody.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

/**
 * @author esotericman
 */
public record ShortUrl(
    @ColumnName(value = "id") Integer id,
    @ColumnName(value = "short_url") String shortUrl,
    @ColumnName(value = "long_url") String longUrl) {}
