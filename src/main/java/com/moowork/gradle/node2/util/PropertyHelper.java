package com.moowork.gradle.node2.util;

import java.io.File;
import java.util.function.Supplier;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

public final class PropertyHelper
{
    public static <T> Property<T> property( final Project project, final Class<T> type )
    {
        return project.getObjects().property( type );
    }

    public static <T> Property<T> property( final Project project, final Class<T> type, final T defValue )
    {
        return property( project, type ).convention( defValue );
    }

    public static <T> Property<T> propertyWithSupplier( final Project project, final Class<T> type, final Supplier<T> defValue )
    {
        return property( project, type ).convention( project.provider( defValue::get ) );
    }

    public static RegularFileProperty fileProperty( final Project project )
    {
        return project.getObjects().fileProperty();
    }

    public static RegularFileProperty fileProperty( final Project project, final File defValue )
    {
        final RegularFileProperty value = fileProperty( project );
        value.set( defValue );
        return value;
    }

    public static DirectoryProperty dirProperty( final Project project )
    {
        return project.getObjects().directoryProperty();
    }

    public static DirectoryProperty dirProperty( final Project project, final File defValue )
    {
        final DirectoryProperty value = dirProperty( project );
        value.set( defValue );
        return value;
    }

    public static <T> ListProperty<T> listProperty( final Project project, final Class<T> type )
    {
        return project.getObjects().listProperty( type );
    }

    public static <K, V> MapProperty<K, V> mapProperty( final Project project, final Class<K> keyType, Class<V> valueType )
    {
        return project.getObjects().mapProperty( keyType, valueType );
    }
}
