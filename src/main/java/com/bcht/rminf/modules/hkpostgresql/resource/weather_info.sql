/*
Navicat PGSQL Data Transfer

Source Server         : 89.50.29.23tcad
Source Server Version : 90602
Source Host           : 89.50.29.23:7017
Source Database       : tcad_tcaddb
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90602
File Encoding         : 65001

Date: 2024-11-21 15:37:35
*/


-- ----------------------------
-- Table structure for weather_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."weather_info";
CREATE TABLE "public"."weather_info" (
"id" varchar(255) COLLATE "default" NOT NULL,
"cloud" varchar(32) COLLATE "default",
"create_time" timestamp(6),
"dew" varchar(32) COLLATE "default",
"feels_like" varchar(32) COLLATE "default",
"humidity" varchar(32) COLLATE "default",
"icon" varchar(32) COLLATE "default",
"location" varchar(64) COLLATE "default",
"obs_time" timestamp(6),
"precip" varchar(32) COLLATE "default",
"pressure" varchar(32) COLLATE "default",
"temp" varchar(32) COLLATE "default",
"text" varchar(32) COLLATE "default",
"vis" varchar(32) COLLATE "default",
"wind360" varchar(32) COLLATE "default",
"wind_dir" varchar(32) COLLATE "default",
"wind_scale" varchar(32) COLLATE "default",
"wind_speed" varchar(32) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of weather_info
-- ----------------------------
INSERT INTO "public"."weather_info" VALUES ('01', '91', '2024-11-21 15:05:44', '-7', '3', '23', '104', '昌都市', '2024-11-21 15:01:00', '0.0', '682', '7', '阴', '30', '180', '南风', '3', '14');
INSERT INTO "public"."weather_info" VALUES ('02', '91', '2024-11-21 14:38:42', '-7', '3', '22', '104', '卡若区', '2024-11-21 14:33:00', '0.0', '682', '7', '阴', '30', '180', '南风', '3', '12');
INSERT INTO "public"."weather_info" VALUES ('03', '91', '2024-11-21 15:20:41', '-10', '5', '16', '104', '察雅县', '2024-11-21 15:18:00', '0.0', '691', '12', '阴', '30', '135', '东南风', '4', '28');
INSERT INTO "public"."weather_info" VALUES ('04', '91', '2024-11-21 14:40:42', '-13', '1', '51', '104', '江达县', '2024-11-21 14:32:00', '0.0', '689', '4', '阴', '30', '180', '南风', '1', '5');
INSERT INTO "public"."weather_info" VALUES ('05', '91', '2024-11-21 15:15:43', '-10', '-5', '38', '104', '类乌齐县', '2024-11-21 15:10:00', '0.0', '642', '2', '阴', '26', '45', '东北风', '4', '26');
INSERT INTO "public"."weather_info" VALUES ('06', '94', '2024-11-21 15:30:43', '-17', '0', '18', '104', '芒康县', '2024-11-21 15:24:00', '0.0', '637', '7', '阴', '27', '180', '南风', '4', '26');
INSERT INTO "public"."weather_info" VALUES ('07', '95', '2024-11-21 14:50:42', '-14', '2', '20', '101', '左贡县', '2024-11-21 14:50:00', '0.0', '641', '5', '多云', '30', '180', '南风', '1', '4');
INSERT INTO "public"."weather_info" VALUES ('08', '91', '2024-11-21 14:35:43', '-12', '-5', '68', '104', '丁青县', '2024-11-21 14:32:00', '0.0', '638', '-2', '阴', '20', '225', '西南风', '1', '3');
INSERT INTO "public"."weather_info" VALUES ('09', '91', '2024-11-21 15:25:44', '-11', '0', '25', '104', '边坝县', '2024-11-21 15:25:00', '0.0', '651', '4', '阴', '26', '45', '东北风', '2', '8');
INSERT INTO "public"."weather_info" VALUES ('10', '91', '2024-11-21 14:45:44', '-11', '-3', '20', '101', '贡觉县', '2024-11-21 14:42:00', '0.0', '651', '6', '多云', '30', '225', '西南风', '5', '37');
INSERT INTO "public"."weather_info" VALUES ('11', '91', '2024-10-10 12:11:58', '3', '14', '36', '101', '洛隆县', '2024-10-10 12:10:00', '0.0', '658', '16', '多云', '30', '270', '西风', '2', '8');
INSERT INTO "public"."weather_info" VALUES ('12', '91', '2024-11-21 14:55:44', '-9', '4', '16', '101', '八宿县', '2024-11-21 14:50:00', '0.0', '669', '9', '多云', '27', '90', '东风', '3', '16');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table weather_info
-- ----------------------------
ALTER TABLE "public"."weather_info" ADD PRIMARY KEY ("id");
