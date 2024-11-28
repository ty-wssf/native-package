package com.bcht.rminf.modules.demo.model;

import com.bcht.rminf.modules.demo.model.common.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Entity
public interface BookStore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    @Key // ❶
    String name();

    @Nullable // ❷
    String website();

    @OneToMany(mappedBy = "store", orderedProps = { // ❸
            @OrderedProp("name"),
            @OrderedProp(value = "edition", desc = true)
    })
    List<Book> books();
}

/*----------------Documentation Links----------------
❶ https://babyfish-ct.github.io/jimmer-doc/docs/mapping/advanced/key
❷ https://babyfish-ct.github.io/jimmer-doc/docs/mapping/base/nullity
❸ https://babyfish-ct.github.io/jimmer-doc/docs/mapping/base/association/one-to-many
❹ ❺ https://babyfish-ct.github.io/jimmer-doc/docs/mapping/advanced/calculated/transient
---------------------------------------------------*/
