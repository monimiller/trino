/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.tests.hive;

import io.prestosql.tempto.ProductTest;

import java.sql.SQLException;
import java.util.OptionalInt;

import static io.prestosql.tests.utils.QueryExecutors.onHive;

public class HiveProductTest
        extends ProductTest
{
    private OptionalInt hiveVersionMajor = OptionalInt.empty();
    private OptionalInt hiveVersionMinor = OptionalInt.empty();

    protected int getHiveVersionMajor()
    {
        if (hiveVersionMajor.isEmpty()) {
            hiveVersionMajor = OptionalInt.of(detectHiveVersionMajor());
        }
        return hiveVersionMajor.getAsInt();
    }

    private static int detectHiveVersionMajor()
    {
        try {
            return onHive().getConnection().getMetaData().getDatabaseMajorVersion();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected int getHiveVersionMinor()
    {
        if (hiveVersionMinor.isEmpty()) {
            hiveVersionMinor = OptionalInt.of(detectHiveVersionMinor());
        }
        return hiveVersionMinor.getAsInt();
    }

    private static int detectHiveVersionMinor()
    {
        try {
            return onHive().getConnection().getMetaData().getDatabaseMinorVersion();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isHiveVersionBefore12()
    {
        return getHiveVersionMajor() == 0
                || (getHiveVersionMajor() == 1 && getHiveVersionMinor() < 2);
    }
}
