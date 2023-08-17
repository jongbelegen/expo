package expo.modules.updates.db.entity

import androidx.room.*
import expo.modules.updates.db.enums.UpdateStatus
import org.json.JSONObject
import java.util.*

/**
 * Data class that represents a (potential) row in the `updates` table in SQLite. The table schema
 * is autogenerated from this file.
 *
 * expo-updates treats most fields (other than `status`, `keep`, `lastAccessed`, and the launch
 * counts) as effectively immutable once in the database. This means an update server should never
 * host two manifests with the same `id` that differ in any other field, as expo-updates will not
 * take the difference into account.
 *
 * The `scopeKey` field is only relevant in environments such as Expo Go in which updates from
 * multiple scopes can be launched.
 */
@Entity(
  tableName = "updates",
  foreignKeys = [
    ForeignKey(
      entity = AssetEntity::class,
      parentColumns = ["id"],
      childColumns = ["launch_asset_id"],
      onDelete = ForeignKey.CASCADE
    )
  ],
  indices = [
    Index(value = ["launch_asset_id"]),
    Index(value = ["scope_key", "commit_time"], unique = true)
  ]
)
class UpdateEntity(
  @field:ColumnInfo(typeAffinity = ColumnInfo.BLOB) @field:PrimaryKey var id: UUID,
  @field:ColumnInfo(name = "commit_time") var commitTime: Date,
  @field:ColumnInfo(name = "runtime_version") var runtimeVersion: String,
  @field:ColumnInfo(name = "scope_key") var scopeKey: String,
  @field:ColumnInfo(name = "manifest") var manifest: JSONObject,
) {
  @ColumnInfo(name = "launch_asset_id")
  var launchAssetId: Long? = null

  var status = UpdateStatus.PENDING

  var keep = false

  val loggingId: String
    get() {
      return id.toString().lowercase()
    }

  @ColumnInfo(name = "last_accessed")
  var lastAccessed: Date = Date()

  @ColumnInfo(name = "successful_launch_count", defaultValue = "0")
  var successfulLaunchCount = 0

  @ColumnInfo(name = "failed_launch_count", defaultValue = "0")
  var failedLaunchCount = 0
}
