package com.bcht.rminf.modules.demo;

import com.bcht.rminf.modules.Fetchers;
import com.bcht.rminf.modules.Immutables;
import com.bcht.rminf.modules.Tables;
import com.bcht.rminf.modules.demo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.babyfish.jimmer.jackson.ImmutableModule;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.tuple.Tuple3;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SqlClientController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @Inject
    JSqlClient sqlClient;

    static {
        Solon.context().subBeansOfType(DataSource.class, dataSource -> {
            try {
                initH2(dataSource);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void initH2(DataSource dataSource) throws Exception {
        try (Connection con = dataSource.getConnection()) {
            DatabaseMetaData metaData = con.getMetaData();
            String productName = metaData.getDatabaseProductName();
            if ("H2".equalsIgnoreCase(productName)) {
                InputStream inputStream = SqlClientController.class
                        .getClassLoader()
                        .getResourceAsStream("h2-database.sql");
                if (inputStream == null) {
                    throw new RuntimeException("no `h2-database.sql`");
                }
                try (Reader reader = new InputStreamReader(inputStream)) {
                    char[] buf = new char[1024];
                    StringBuilder builder = new StringBuilder();
                    while (true) {
                        int len = reader.read(buf);
                        if (len == -1) {
                            break;
                        }
                        builder.append(buf, 0, len);
                    }
                    con.createStatement().execute(builder.toString());
                }
            }
        }
    }

    /**
     * 常规的属性查询
     *
     * @return
     * @throws JsonProcessingException
     */
    @Mapping("/sqlClient1")
    public List<Tuple3<Long, String, Integer>> sqlClient1() throws JsonProcessingException {
        BookTable table = Tables.BOOK_TABLE;
        List<Tuple3<Long, String, Integer>> tuples = sqlClient
                .createQuery(table)
                .where(table.edition().eq(3))
                .select(
                        table.id(),
                        table.name(),
                        table.edition()
                )
                .execute();
        return tuples;
    }

    /**
     * 对象抓取器
     *
     * @return
     * @throws JsonProcessingException
     */
    @Mapping("/sqlClient2")
    public String sqlClient2() throws JsonProcessingException {
        BookTable table = BookTable.$;

        List<Book> books = sqlClient
                .createQuery(table)
                .where(table.name().eq("GraphQL in Action"))
                .orderBy(table.edition().desc())
                .select(
                        table.fetch(
                                BookFetcher.$
                                        // `id()`是隐含的，总是被查询
                                        .name()
                                        .edition()
                                        .price()
                        )
                )
                .execute();
        // 系列化
        return books.toString();
    }

    @Mapping("/sqlClient3")
    public String sqlClient3() throws JsonProcessingException {
        BookTable table = Tables.BOOK_TABLE;
        List<Book> books = sqlClient.createQuery(table)
                .where(table.edition().eq(3))
                .select(
                        table.fetch(
                                Fetchers.BOOK_FETCHER
                                        .name()
                                        .authors(
                                                Fetchers.AUTHOR_FETCHER
                                                        .firstName()
                                        )
                        )
                )
                .execute();
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ImmutableModule());
        // 系列化
        return mapper.writeValueAsString(books);
    }

    @Mapping("/sqlClient4")
    public int sqlClient4() {
        AuthorTable author = Tables.AUTHOR_TABLE;

        int affectedRowCount = sqlClient
                .createUpdate(author)
                .set(
                        author.firstName(),
                        author.firstName().concat("*")
                )
                .where(author.firstName().eq("Dan"))
                .execute();
        System.out.println("Affected row count: " + affectedRowCount);
        return affectedRowCount;
    }

    @Mapping("/sqlClient5")
    public int sqlClient5() {
        Book simpleBook = Immutables.createBook(draft -> {
            draft.setName("SQL in Action");
            draft.setEdition(1);
            draft.setPrice(new BigDecimal("59.9"));
            draft.setTenant("t");
            draft.setCreatedTime(LocalDateTime.now());
            draft.setModifiedTime(LocalDateTime.now());
        });
        sqlClient.save(simpleBook);
        return 1;
    }

    /**
     * 递归查询
     *
     * @return
     * @throws JsonProcessingException
     */
    @Mapping("/sqlClient6")
    public String sqlClient6() throws JsonProcessingException {
        TreeNodeTable table = TreeNodeTable.$;

        List<TreeNode> rootNodes = sqlClient
                .createQuery(table)
                .where(table.parentId().isNull())
                .select(
                        table.fetch(
                                TreeNodeFetcher.$
                                        .allScalarFields()
                                        .recursiveChildNodes()
                        )
                )
                .execute();
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ImmutableModule())
                .registerModule(new JavaTimeModule());
        // 系列化
        return mapper.writeValueAsString(rootNodes);
    }

}
