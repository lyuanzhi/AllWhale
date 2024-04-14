CREATE DATABASE  IF NOT EXISTS `OxceanDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `OxceanDB`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 34.41.117.214    Database: OxceanDB
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `activity_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `activity_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `max_people` int DEFAULT NULL,
  `activity_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `activity_fee` double DEFAULT NULL,
  `img_number` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  `is_deleted` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES ('221001172430823784448','o3pH95eSirfYzUW2sP61ohzKgay4','test','test','娱乐局','2022-10-01 22: 50',1,'家',200,0,1664635821424,1,1),('221001174558308990976','o3pH95eSirfYzUW2sP61ohzKgay4','test','test','娱乐局','2022-10-22 23: 07',1,'去',12,1,1664636835888,1,1),('22100244080897720320','o3pH95byUNGnFlb7uC2I8rraUenU','测试发布','狂野飙车','娱乐局','2022-10-01 22: 50',10,'张三家',10,1,1664661019410,0,0),('23101423484539338752','o3pH95eSirfYzUW2sP61ohzKgay4','123',NULL,'娱乐局','2023-10-20 23: 06',NULL,NULL,NULL,1,1697252798301,0,0),('24041466777641910272','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','Hiking','Let’s go hiking tomorrow ','运动局','2024-04-15 04: 50',5,NULL,NULL,1,1713084642061,0,0);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_collection`
--

DROP TABLE IF EXISTS `activity_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_collection` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `activity_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_collection`
--

LOCK TABLES `activity_collection` WRITE;
/*!40000 ALTER TABLE `activity_collection` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `parent_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `community_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `is_anonymous` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  `is_checked` int DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES ('221001172302457110528','none','o3pH95eSirfYzUW2sP61ohzKgay4','221001172198509674496','wow',1,1664635760215,1,0),('22100301672598257664','none','o3pH95eSirfYzUW2sP61ohzKgay4','221002172461809205248','不会诶?',0,1664727197558,1,1),('22100301901695336448','none','o3pH95eSirfYzUW2sP61ohzKgay4','221002172461809205248','嗯',1,1664727306799,0,1),('22100333359908569088','none','o3pH95eSirfYzUW2sP61ohzKgay4','22100333336665833472','hello',1,1664742307244,1,1),('22100386891061837824','none','o3pH95ckAWvhe6a5J3p_u6H86XmE','221001172198509674496','戴森球吗？',1,1664767832887,1,0),('22100387238501203968','22100386891061837824','o3pH95ckAWvhe6a5J3p_u6H86XmE','221001172198509674496','是',1,1664767998559,1,0),('22100387298205024256','none','o3pH95ckAWvhe6a5J3p_u6H86XmE','221001171850371956736','在',1,1664768027029,1,1),('221208131685259673600','none','o3pH95S5ujN--gfvTM9SI-0dGqz8','221030169510264897536','嘻嘻嘻',1,1670491592425,1,0),('221208131818915364864','none','o3pH95eSirfYzUW2sP61ohzKgay4','221030169510264897536','进的来嘛？',1,1670491656158,1,0),('24041466222358003712','none','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','24041466123290640384','So pretty ',1,1713084377281,0,0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_heart`
--

DROP TABLE IF EXISTS `comment_heart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_heart` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `comment_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_heart`
--

LOCK TABLES `comment_heart` WRITE;
/*!40000 ALTER TABLE `comment_heart` DISABLE KEYS */;
INSERT INTO `comment_heart` VALUES ('221001172310013149184','o3pH95eSirfYzUW2sP61ohzKgay4','221001172302457110528',0,1664635763815),('221001178623036260352','o3pH95TieWohNIh2r5tMmzFtfkKs','221001172302457110528',0,1664638774101);
/*!40000 ALTER TABLE `comment_heart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community`
--

DROP TABLE IF EXISTS `community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `is_anonymous` int DEFAULT NULL,
  `img_number` int DEFAULT NULL,
  `its_tags` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community`
--

LOCK TABLES `community` WRITE;
/*!40000 ALTER TABLE `community` DISABLE KEYS */;
INSERT INTO `community` VALUES ('1','1','ALLWHALE小程序上线啦！',1,0,';',1,1),('221001170288375398400','o3pH95eSirfYzUW2sP61ohzKgay4','hello',0,0,';',1664634800125,1),('221001171291774550016','o3pH95eSirfYzUW2sP61ohzKgay4','我们都是阴沟里的虫子',1,0,';emo;',1664635278624,0),('221001171850371956736','o3pH95eSirfYzUW2sP61ohzKgay4','虫子从来没有被真正战胜过',1,0,';emo;',1664635544978,0),('221001172198509674496','o3pH95eSirfYzUW2sP61ohzKgay4','球',0,1,';学术;',1664635711315,1),('22100216758777839616','o3pH95ZbmZSBAurN4X2-U3A3_Ckk','LYZ yyds\n',0,1,';帅哥美女;学术;emo;',1664647991662,0),('221002171526273892352','o3pH95eSirfYzUW2sP61ohzKgay4','核酸检测报告出的好慢啊，又没赶上??',1,0,';emo;生活;',1664721790655,1),('221002172461809205248','o3pH95eSirfYzUW2sP61ohzKgay4','?',0,1,';学术;',1664722237276,1),('221002176708101603328','o3pH95eSirfYzUW2sP61ohzKgay4','hello',1,0,';搞笑;',1664724261458,1),('221002176729752600576','o3pH95eSirfYzUW2sP61ohzKgay4','hello',0,0,';',1664724271703,1),('221002180187870265344','o3pH95eSirfYzUW2sP61ohzKgay4','腾讯云又攻击咱',0,0,';',1664725920805,1),('22100222672134111232','o3pH95dKOD4NFn6Euho3_ZeC4kRo','太真实了',1,1,';搞笑;',1664650810955,0),('22100222743879778304','o3pH95dKOD4NFn6Euho3_ZeC4kRo','可以',1,1,';生活;',1664650845162,0),('22100222940280160256','o3pH95dKOD4NFn6Euho3_ZeC4kRo','Sad',1,1,';',1664650938813,0),('22100243896478367744','o3pH95byUNGnFlb7uC2I8rraUenU','猫猫的小程序很精彩！',1,0,';搞笑;运动;',1664660931524,0),('22100333336665833472','o3pH95eSirfYzUW2sP61ohzKgay4','hello',1,0,';',1664742296433,1),('221030169510264897536','o3pH95dzAsxWjBvb3SR9CgdDAaGk','ilzy',1,0,';',1667140029070,0),('23101407602127765504','o3pH95eSirfYzUW2sP61ohzKgay4','return',1,1,';学术;',1697245225104,1),('23101408366772453376','o3pH95eSirfYzUW2sP61ohzKgay4','rrrturn',1,1,';搞笑;',1697245589665,1),('23101409241226117120','o3pH95eSirfYzUW2sP61ohzKgay4','return!',1,1,';学术;',1697246006650,1),('23101409691375599616','o3pH95eSirfYzUW2sP61ohzKgay4','hi',1,1,';',1697246221288,1),('23101410312082259968','o3pH95eSirfYzUW2sP61ohzKgay4','hi',1,1,';帅哥美女;',1697246517258,1),('23101410440109195264','o3pH95eSirfYzUW2sP61ohzKgay4','hi',1,1,';八卦;',1697246578313,0),('24041466056162902016','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','This is just a test?',1,0,';搞笑;',1713084298110,0),('24041466123290640384','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','I love Pom Pom!!!',1,1,';',1713084330132,0);
/*!40000 ALTER TABLE `community` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_heart`
--

DROP TABLE IF EXISTS `community_heart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community_heart` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `community_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_heart`
--

LOCK TABLES `community_heart` WRITE;
/*!40000 ALTER TABLE `community_heart` DISABLE KEYS */;
INSERT INTO `community_heart` VALUES ('221001172308694040576','o3pH95eSirfYzUW2sP61ohzKgay4','221001172198509674496',0,1664635763186),('221001172335745204224','o3pH95eSirfYzUW2sP61ohzKgay4','1',0,1664635776086),('221001172337018175488','o3pH95eSirfYzUW2sP61ohzKgay4','221001171850371956736',0,1664635776693),('221001172338356158464','o3pH95eSirfYzUW2sP61ohzKgay4','221001171291774550016',0,1664635777331),('221001172340765786112','o3pH95eSirfYzUW2sP61ohzKgay4','221001170288375398400',0,1664635778480),('221001178604698763264','o3pH95TieWohNIh2r5tMmzFtfkKs','221001172198509674496',0,1664638774104),('221001178632018362368','o3pH95TieWohNIh2r5tMmzFtfkKs','221001171850371956736',0,1664638778384),('221002174309324619776','o3pH95SMXXjVDb0TN9hF2H6IGyQc','221002172461809205248',0,1664723125992),('221002174336803602432','o3pH95SMXXjVDb0TN9hF2H6IGyQc','221002171526273892352',0,1664723130266),('221002176996998971392','o3pH95ZmNkbPak78stlEvOSoRwhA','22100222940280160256',0,1664724407431),('221002177024180158464','o3pH95ZmNkbPak78stlEvOSoRwhA','22100222743879778304',0,1664724411707),('22100244499338264576','o3pH95byUNGnFlb7uC2I8rraUenU','22100222940280160256',0,1664661218938),('22100302006573907968','o3pH95TieWohNIh2r5tMmzFtfkKs','221002172461809205248',0,1664727365413),('22100302033673306112','o3pH95TieWohNIh2r5tMmzFtfkKs','221002171526273892352',0,1664727369730),('221030169580550946816','o3pH95dzAsxWjBvb3SR9CgdDAaGk','221030169510264897536',0,1667140062308),('24041466192188375040','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','24041466123290640384',0,1713084362892);
/*!40000 ALTER TABLE `community_heart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_tags`
--

DROP TABLE IF EXISTS `community_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community_tags` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_tags`
--

LOCK TABLES `community_tags` WRITE;
/*!40000 ALTER TABLE `community_tags` DISABLE KEYS */;
INSERT INTO `community_tags` VALUES ('1','搞笑',1),('2','帅哥美女',2),('3','学术',3),('4','emo',4),('5','运动',5),('6','日常拍',6),('7','生活',7),('8','八卦',8);
/*!40000 ALTER TABLE `community_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_tracking`
--

DROP TABLE IF EXISTS `event_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_tracking` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `user_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `user_event` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_tracking`
--

LOCK TABLES `event_tracking` WRITE;
/*!40000 ALTER TABLE `event_tracking` DISABLE KEYS */;
INSERT INTO `event_tracking` VALUES ('221001170125110018048','o3pH95eSirfYzUW2sP61ohzKgay4','210.17.224.107','login',1664634721975),('221001170288983572480','o3pH95eSirfYzUW2sP61ohzKgay4','210.17.224.107','community/addCommunity (content: hello)',1664634800116),('221001171292464513024','o3pH95eSirfYzUW2sP61ohzKgay4','210.17.224.107','community/addCommunity (content: 我们都是阴沟里的虫子)',1664635278612),('221001171851055628288','o3pH95eSirfYzUW2sP61ohzKgay4','220.191.168.22','community/addCommunity (content: 虫子从来没有被真正战胜过)',1664635544969),('221001172199891697664','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/addCommunity (content: 球)',1664635711307),('221001172302434041856','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/addComment (communityId: 221001172198509674496)',1664635760203),('221001172308675166208','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/changeCommunityHeart (communityId: 221001172198509674496)',1664635763179),('221001172309987983360','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/changeCommentHeart (commentId: 221001172302457110528)',1664635763805),('221001172335728427008','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/changeCommunityHeart (communityId: 1)',1664635776079),('221001172336993009664','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/changeCommunityHeart (communityId: 221001171850371956736)',1664635776682),('221001172338335186944','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/changeCommunityHeart (communityId: 221001171291774550016)',1664635777322),('221001172340751106048','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/changeCommunityHeart (communityId: 221001170288375398400)',1664635778474),('221001172430804910080','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','activity/addActivity (title: test)',1664635821416),('221001172500514242560','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','activity/deleteActivity (activityId: 221001172430823784448)',1664635854655),('221001172526449721344','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/deleteCommunity (communityId: 221001170288375398400)',1664635867022),('221001172699554938880','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','goods/addGoods (title: test)',1664635949565),('221001172880358801408','o3pH95eSirfYzUW2sP61ohzKgay4','210.17.224.107','goods/addGoods (title: test)',1664636035780),('221001173197439795200','o3pH95eSirfYzUW2sP61ohzKgay4','220.191.168.22','goods/addGoods (title: test)',1664636186975),('221001173246244716544','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','goods/deleteGoods (goodsId: 221001173196028411904)',1664636210248),('221001173361797791744','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','goods/addGoods (title: test)',1664636265348),('221001173382477807616','o3pH95eSirfYzUW2sP61ohzKgay4','220.191.168.22','goods/deleteGoods (goodsId: 221001173361174937600)',1664636275208),('221001173750427320320','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/addCommunity (content: 去)',1664636450660),('221001174427329265664','o3pH95eSirfYzUW2sP61ohzKgay4','220.191.168.22','login',1664636773432),('221001174558296408064','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','activity/addActivity (title: test)',1664636835882),('221001174584166875136','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','activity/deleteActivity (activityId: 221001174558308990976)',1664636848218),('221001176370351079424','o3pH95eSirfYzUW2sP61ohzKgay4','210.17.224.107','login',1664637699937),('221001177510377586688','o3pH95eSirfYzUW2sP61ohzKgay4','210.17.224.107','login',1664638243545),('221001178597591515136','o3pH95TieWohNIh2r5tMmzFtfkKs','81.68.168.196','login',1664638761968),('221001178604684083200','o3pH95TieWohNIh2r5tMmzFtfkKs','49.234.14.196','community/changeCommunityHeart (communityId: 221001172198509674496)',1664638765350),('221001178623019483136','o3pH95TieWohNIh2r5tMmzFtfkKs','49.235.97.213','community/changeCommentHeart (commentId: 221001172302457110528)',1664638774093),('221001178623027871744','o3pH95TieWohNIh2r5tMmzFtfkKs','49.234.14.196','community/changeCommunityHeart (communityId: 221001172198509674496)',1664638774097),('221001178623063523328','o3pH95TieWohNIh2r5tMmzFtfkKs','49.234.54.15','community/deleteCommunity (communityId: 221001172198509674496)',1664638774114),('221001178632001585152','o3pH95TieWohNIh2r5tMmzFtfkKs','49.234.54.15','community/changeCommunityHeart (communityId: 221001171850371956736)',1664638778376),('221001178632005779456','o3pH95TieWohNIh2r5tMmzFtfkKs','49.234.14.196','community/deleteCommunity (communityId: 221001171850371956736)',1664638778378),('22100216652078940160','o3pH95ZbmZSBAurN4X2-U3A3_Ckk','223.104.161.222','login',1664647940330),('22100216759715266560','o3pH95ZbmZSBAurN4X2-U3A3_Ckk','223.104.161.222','community/addCommunity (content: LYZ yyds\n)',1664647991655),('22100216760394743808','o3pH95ZvCWQbOtrIWDakZ1_n2P0o','210.83.72.36','login',1664647991979),('22100216761260867584','o3pH95a-RkZZy3oX8ILUZPHeD1QM','220.191.168.24','login',1664647992392),('221002171527406354432','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.135','community/addCommunity (content: 核酸检测报告出的好慢啊，又没赶上??)',1664721790641),('221002172310231252992','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.135','login',1664722163922),('221002172464048963584','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.135','community/addCommunity (content: ?)',1664722237268),('221002174302263508992','o3pH95SMXXjVDb0TN9hF2H6IGyQc','81.68.167.172','login',1664723113796),('221002174309305745408','o3pH95SMXXjVDb0TN9hF2H6IGyQc','81.68.167.172','community/changeCommunityHeart (communityId: 221002172461809205248)',1664723117154),('221002174327815208960','o3pH95SMXXjVDb0TN9hF2H6IGyQc','81.68.168.168','community/deleteCommunity (communityId: 221002172461809205248)',1664723125980),('221002174327823597568','o3pH95SMXXjVDb0TN9hF2H6IGyQc','81.68.167.172','community/changeCommunityHeart (communityId: 221002172461809205248)',1664723125984),('221002174336780533760','o3pH95SMXXjVDb0TN9hF2H6IGyQc','81.68.168.168','community/deleteCommunity (communityId: 221002171526273892352)',1664723130255),('221002174336786825216','o3pH95SMXXjVDb0TN9hF2H6IGyQc','81.68.167.172','community/changeCommunityHeart (communityId: 221002171526273892352)',1664723130258),('221002174426484113408','o3pH95SMXXjVDb0TN9hF2H6IGyQc','150.158.195.143','goods/addWanted (goodsId: 22100244352512458752)',1664723173029),('221002174426519764992','o3pH95SMXXjVDb0TN9hF2H6IGyQc','81.68.167.172','goods/changeCollection (goodsId: 22100244352512458752)',1664723173046),('221002174427000012800','o3pH95SMXXjVDb0TN9hF2H6IGyQc','150.158.195.143','goods/cancelGoods (goodsId: 22100244352512458752)',1664723173275),('221002174427115356160','o3pH95SMXXjVDb0TN9hF2H6IGyQc','81.68.167.172','goods/deleteGoods (goodsId: 22100244352512458752)',1664723173330),('221002176709066293248','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.60','community/addCommunity (content: hello)',1664724261450),('221002176730557906944','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.60','community/addCommunity (content: hello)',1664724271697),('221002176990189518848','o3pH95ZmNkbPak78stlEvOSoRwhA','175.24.19.129','login',1664724395499),('221002176996982194176','o3pH95ZmNkbPak78stlEvOSoRwhA','175.24.19.129','community/changeCommunityHeart (communityId: 22100222940280160256)',1664724398738),('221002177015181279232','o3pH95ZmNkbPak78stlEvOSoRwhA','175.24.19.129','community/deleteCommunity (communityId: 22100222940280160256)',1664724407416),('221002177015195959296','o3pH95ZmNkbPak78stlEvOSoRwhA','175.24.97.90','community/changeCommunityHeart (communityId: 22100222940280160256)',1664724407423),('221002177024161284096','o3pH95ZmNkbPak78stlEvOSoRwhA','175.24.19.129','community/changeCommunityHeart (communityId: 22100222743879778304)',1664724411698),('221002177024165478400','o3pH95ZmNkbPak78stlEvOSoRwhA','175.24.19.32','community/deleteCommunity (communityId: 22100222743879778304)',1664724411700),('221002180188973367296','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.60','community/addCommunity (content: 腾讯云又攻击咱)',1664725920798),('221002180226998927360','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.60','community/deleteCommunity (communityId: 221002180187870265344)',1664725938930),('22100222672201220096','o3pH95dKOD4NFn6Euho3_ZeC4kRo','137.220.76.57','community/addCommunity (content: 太真实了)',1664650810948),('22100222743938498560','o3pH95dKOD4NFn6Euho3_ZeC4kRo','137.220.76.57','community/addCommunity (content: 可以)',1664650845155),('22100222940338880512','o3pH95dKOD4NFn6Euho3_ZeC4kRo','137.220.76.57','community/addCommunity (content: Sad)',1664650938806),('22100223064968429568','o3pH95dKOD4NFn6Euho3_ZeC4kRo','137.220.76.57','login',1664650998234),('22100243630586757120','o3pH95byUNGnFlb7uC2I8rraUenU','5.151.46.38','login',1664660804685),('22100243896572739584','o3pH95byUNGnFlb7uC2I8rraUenU','5.151.46.38','community/addCommunity (content: 猫猫的小程序很精彩！)',1664660931517),('22100244080885137408','o3pH95byUNGnFlb7uC2I8rraUenU','5.151.46.38','activity/addActivity (title: 测试发布)',1664661019404),('22100244352776699904','o3pH95byUNGnFlb7uC2I8rraUenU','5.151.46.38','goods/addGoods (title: 拼多多)',1664661149052),('22100244499325681664','o3pH95byUNGnFlb7uC2I8rraUenU','5.151.46.38','community/changeCommunityHeart (communityId: 22100222940280160256)',1664661218932),('22100301672581480448','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.60','community/addComment (communityId: 221002172461809205248)',1664727197550),('22100301721229115392','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.60','community/deleteComment (commentId: 22100301672598257664)',1664727220746),('22100301901676462080','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.60','community/addComment (communityId: 221002172461809205248)',1664727306791),('22100301910612426752','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.60','community/deleteComment (commentId: 22100301901695336448)',1664727311051),('22100301999496019968','o3pH95TieWohNIh2r5tMmzFtfkKs','118.89.77.117','login',1664727353434),('22100302006559227904','o3pH95TieWohNIh2r5tMmzFtfkKs','123.206.218.196','community/changeCommunityHeart (communityId: 221002172461809205248)',1664727356802),('22100302024601026560','o3pH95TieWohNIh2r5tMmzFtfkKs','123.206.218.196','community/changeCommunityHeart (communityId: 221002172461809205248)',1664727365405),('22100302024687009792','o3pH95TieWohNIh2r5tMmzFtfkKs','118.89.77.117','community/deleteCommunity (communityId: 221002172461809205248)',1664727365446),('22100302033583128576','o3pH95TieWohNIh2r5tMmzFtfkKs','123.206.218.196','community/deleteCommunity (communityId: 221002171526273892352)',1664727369688),('22100302033656528896','o3pH95TieWohNIh2r5tMmzFtfkKs','118.89.77.117','community/changeCommunityHeart (communityId: 221002171526273892352)',1664727369724),('22100302123452383232','o3pH95TieWohNIh2r5tMmzFtfkKs','123.206.218.196','goods/addWanted (goodsId: 22100244352512458752)',1664727412541),('22100302124012322816','o3pH95TieWohNIh2r5tMmzFtfkKs','123.206.218.196','goods/changeCollection (goodsId: 22100244352512458752)',1664727412808),('22100302124572262400','o3pH95TieWohNIh2r5tMmzFtfkKs','123.206.218.196','goods/cancelGoods (goodsId: 22100244352512458752)',1664727413076),('22100302125130104832','o3pH95TieWohNIh2r5tMmzFtfkKs','123.206.218.196','goods/deleteGoods (goodsId: 22100244352512458752)',1664727413342),('22100332859802828800','o3pH95eSirfYzUW2sP61ohzKgay4','220.191.168.22','goods/addGoods (title: 荞麦面)',1664742068776),('22100333244219179008','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/deleteCommunity (communityId: 221002172461809205248)',1664742252079),('22100333337219481600','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/addCommunity (content: hello)',1664742296425),('22100333359891791872','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/addComment (communityId: 22100333336665833472)',1664742307237),('22100333413815861248','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/deleteComment (commentId: 22100333359908569088)',1664742332949),('22100333418846928896','o3pH95eSirfYzUW2sP61ohzKgay4','210.83.72.33','community/deleteCommunity (communityId: 22100333336665833472)',1664742335348),('22100382506986553344','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.89','community/deleteCommunity (communityId: 221002171526273892352)',1664765742397),('22100386535651196928','o3pH95ckAWvhe6a5J3p_u6H86XmE','183.137.49.210','login',1664767663414),('22100386642704515072','o3pH95ckAWvhe6a5J3p_u6H86XmE','183.137.49.210','goods/addWanted (goodsId: 22100332858276102144)',1664767714461),('22100386891047157760','o3pH95ckAWvhe6a5J3p_u6H86XmE','183.137.49.210','community/addComment (communityId: 221001172198509674496)',1664767832880),('22100387238482329600','o3pH95ckAWvhe6a5J3p_u6H86XmE','183.137.49.210','community/addComment (communityId: 221001172198509674496)',1664767998550),('22100387298188247040','o3pH95ckAWvhe6a5J3p_u6H86XmE','183.137.49.210','community/addComment (communityId: 221001171850371956736)',1664768027021),('22100387317316370432','o3pH95ckAWvhe6a5J3p_u6H86XmE','183.137.49.210','community/deleteComment (commentId: 22100387298205024256)',1664768036141),('22100387437883736064','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.89','goods/cancelGoods (goodsId: 22100332858276102144)',1664768093632),('22100387500773130240','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.89','goods/deleteGoods (goodsId: 22100332858276102144)',1664768123620),('22100812556775194624','o3pH95dKOD4NFn6Euho3_ZeC4kRo','86.167.90.154','goods/addWanted (goodsId: 22100244352512458752)',1665164387537),('22100814006146301952','o3pH95TD_4laAz5_Y0-uLzqb-16I','86.167.90.154','login',1665165078651),('221014116120143003648','o3pH95dKOD4NFn6Euho3_ZeC4kRo','137.220.78.212','login',1665732170400),('221014130935695605760','o3pH95WbrvqNzUjrGH4tZWjX3t2A','114.219.151.132','login',1665739235006),('22101591875776135168','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.163','login',1665807009784),('221030169331952451584','o3pH95dzAsxWjBvb3SR9CgdDAaGk','210.83.72.25','login',1667139943767),('221030169510829031424','o3pH95dzAsxWjBvb3SR9CgdDAaGk','210.83.72.25','community/addCommunity (content: ilzy)',1667140029062),('221030169580527878144','o3pH95dzAsxWjBvb3SR9CgdDAaGk','210.83.72.25','community/changeCommunityHeart (communityId: 221030169510264897536)',1667140062298),('22110942136768610304','o3pH95TD_4laAz5_Y0-uLzqb-16I','82.13.184.2','login',1667943292377),('221208131164023029760','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.224','login',1670491343880),('221208131441654497280','o3pH95S5ujN--gfvTM9SI-0dGqz8','58.40.234.114','login',1670491476265),('221208131685232410624','o3pH95S5ujN--gfvTM9SI-0dGqz8','58.40.234.114','community/addComment (communityId: 221030169510264897536)',1670491592412),('221208131818902781952','o3pH95eSirfYzUW2sP61ohzKgay4','223.104.161.224','community/addComment (communityId: 221030169510264897536)',1670491656151),('22121052942847606784','o3pH95Qhui9aK3hDcBAQt_75skWg','192.76.8.72','login',1670626845117),('23101407434881990656','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','login',1697245145229),('23101407602339577856','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/addCommunity (content: return)',1697245225079),('23101408366906671104','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/addCommunity (content: rrrturn)',1697245589653),('23101409159653195776','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/deleteCommunity (communityId: 23101408366772453376)',1697245967664),('23101409169493032960','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/deleteCommunity (communityId: 23101407602127765504)',1697245972355),('23101409176736595968','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/deleteCommunity (communityId: 221001172198509674496)',1697245975809),('23101409241387597824','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/addCommunity (content: return!)',1697246006637),('23101409464956583936','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','goods/addGoods (title: picture)',1697246113244),('23101409691522400256','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/addCommunity (content: hi)',1697246221278),('23101410312210186240','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/addCommunity (content: hi)',1697246517245),('23101410440251801600','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/addCommunity (content: hi)',1697246578301),('23101410509432651776','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','goods/deleteGoods (goodsId: 23101409464793006080)',1697246611288),('23101410524215476224','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/deleteCommunity (communityId: 23101410312082259968)',1697246618337),('23101410528156024832','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/deleteCommunity (communityId: 23101409691375599616)',1697246620216),('23101410541575700480','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','community/deleteCommunity (communityId: 23101409241226117120)',1697246626615),('23101423484482715648','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','activity/addActivity (title: 123)',1697252798274),('23101423569807441920','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','goods/addGoods (title: hi)',1697252838960),('23101423625442787328','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','goods/addGoods (title: hello)',1697252865489),('24041415915297013760','o3pH95eSirfYzUW2sP61ohzKgay4','192.168.1.1','login',1713074789006),('24041415973675433984','o3pH95eSirfYzUW2sP61ohzKgay4','192.168.1.1','login',1713074816843),('24041422041168183296',NULL,'192.168.1.1','login',1713077710050),('24041422387221331968','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','192.168.1.1','login',1713077875060),('24041449411675652096','o3pH95eSirfYzUW2sP61ohzKgay4','174.99.158.118','login',1713076361352),('24041451284772126720',NULL,'174.99.158.118','login',1713077254485),('24041451439166554112',NULL,'174.99.158.118','login',1713077328106),('24041451649473150976',NULL,'174.99.158.118','login',1713077428388),('24041451878123536384',NULL,'174.99.158.118','login',1713077537418),('24041453792770883584','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','login',1713078450394),('24041465764394532864','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','login',1713084158907),('24041466056292925440','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','community/addCommunity (content: This is just a test?)',1713084298096),('24041466123437441024','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','community/addCommunity (content: I love Pom Pom!!!)',1713084330112),('24041466192150626304','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','community/changeCommunityHeart (communityId: 24041466123290640384)',1713084362878),('24041466222326546432','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','community/addComment (communityId: 24041466123290640384)',1713084377267),('24041466777595772928','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','activity/addActivity (title: Hiking)',1713084642039),('24041466979807363072','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','goods/addGoods (title: Cat)',1713084738461),('24041467059069222912','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','goods/addGoods (title: Food)',1713084776257),('24041467132970762240','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','goods/addGoods (title: Chicken )',1713084811496),('24041467239394934784','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','goods/addGoods (title: Tire)',1713084862242),('24041467767522820096','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','goods/addWanted (goodsId: 22100244352512458752)',1713085114073),('24041467782519554048','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','174.99.158.118','goods/addWanted (goodsId: 23101423625233072128)',1713085121225);
/*!40000 ALTER TABLE `event_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `func_config`
--

DROP TABLE IF EXISTS `func_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `func_config` (
  `id` int NOT NULL,
  `func_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `func_value` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `func_config`
--

LOCK TABLES `func_config` WRITE;
/*!40000 ALTER TABLE `func_config` DISABLE KEYS */;
INSERT INTO `func_config` VALUES (0,'匿名',1),(1,'其他',0);
/*!40000 ALTER TABLE `func_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `wx_number` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `detailed_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `price` double DEFAULT NULL,
  `old_price` double DEFAULT NULL,
  `img_number` int DEFAULT NULL,
  `second_hand_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '二手类型：闲置、租房',
  `goods_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品类型：美妆、日用、厨具、电子、其他',
  `how_new_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '新旧：全新、几乎全新、略微使用、日常折旧',
  `pick_up_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '取货：自取、上门',
  `location_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '位置：市中心、Cowley、Summertown、Botley、Hinksey、其他',
  `house_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '房型：Flat、Studio、House',
  `ensuite_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否独卫：独卫、非独卫',
  `price_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '价格/周：<£150、£150-£200、£200-£250、>£250',
  `tenancy_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '租期：<7天、7天-1个月、1-3月、>3月',
  `create_time` bigint DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  `is_deleted` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES ('221001173196028411904','o3pH95eSirfYzUW2sP61ohzKgay4','','test','test',20,2,1,'闲置','美妆','全新','自取','市中心',NULL,NULL,NULL,NULL,1664636186982,1,1),('221001173361174937600','o3pH95eSirfYzUW2sP61ohzKgay4','','test','test',10,NULL,1,'租房',NULL,NULL,NULL,'Cowley','Flat','独卫','<£150','<7天',1664636265355,1,1),('22100244352512458752','o3pH95byUNGnFlb7uC2I8rraUenU','13322','拼多多','',100,120,1,'闲置','电子','全新','自取','市中心',NULL,NULL,NULL,NULL,1664661149059,0,0),('22100332858276102144','o3pH95eSirfYzUW2sP61ohzKgay4','','荞麦面','减脂',10,20,1,'闲置','其他','略微使用','自取','市中心',NULL,NULL,NULL,NULL,1664742068783,1,1),('23101409464793006080','o3pH95eSirfYzUW2sP61ohzKgay4','','picture','',100000,NULL,1,'闲置','美妆','全新','自取','Summertown',NULL,NULL,NULL,NULL,1697246113258,1,1),('23101423569671127040','o3pH95eSirfYzUW2sP61ohzKgay4','','hi','',12,NULL,1,'租房',NULL,NULL,NULL,'Summertown','Flat','独卫','<£150','<7天',1697252838975,0,0),('23101423625233072128','o3pH95eSirfYzUW2sP61ohzKgay4','','hello','',1,NULL,1,'闲置','美妆','全新','自取','Summertown',NULL,NULL,NULL,NULL,1697252865501,0,0),('24041466979668951040','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','','Cat','',100,200,1,'闲置','其他','全新','送货上门','Cowley',NULL,NULL,NULL,NULL,1713084738476,0,0),('24041467058949685248','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','','Food','',5,10,1,'闲置','日用','全新','送货上门','市中心',NULL,NULL,NULL,NULL,1713084776273,0,0),('24041467132853321728','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','','Chicken ','',100,10,1,'闲置','日用','全新','送货上门','市中心',NULL,NULL,NULL,NULL,1713084811510,0,0),('24041467239271202816','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','','Tire','',500,1000,1,'闲置','其他','日常折旧','自取','Headington',NULL,NULL,NULL,NULL,1713084862257,0,0);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `from_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `to_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `goods_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `activity_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `img_number` int DEFAULT NULL,
  `is_checked` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES ('24041467798533406720','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','o3pH95eSirfYzUW2sP61ohzKgay4','23101423625233072128',NULL,'Hi',1,0,1713085128860),('24041467810745122816','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','o3pH95eSirfYzUW2sP61ohzKgay4','23101423625233072128',NULL,'I want this',1,0,1713085134683);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像url',
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('o3pH95Qhui9aK3hDcBAQt_75skWg','夜','https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erFKWPUBX4I41vboChx0KhDjdl2UE5XKUORGMADI7SaTkD5AGKVBF2xpqko4victoLzc8k2ENtibusw/132',1670626845122,1670626845122,0),('o3pH95S5ujN--gfvTM9SI-0dGqz8','?','https://thirdwx.qlogo.cn/mmopen/vi_32/5ibGl3eiaU3QAoqxMy08d4GNxGFyTDUFupsgHgP6ic0Gy1Xg9ibhGoBQoEd6qeHS4tldPerzQGiatZcQeUVtQN8fjlg/132',1670491476273,1670491476273,0),('o3pH95SMXXjVDb0TN9hF2H6IGyQc','蓝色妖姬','https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELJiaN9ZJHQcSu7ZbEOuw1icpp1aulyPQ7RgC2DktqbJdsCXLClf5gpSo2AOIVySFohhM9mQicE87Y0Q/132',1664723113810,1664723113810,0),('o3pH95TD_4laAz5_Y0-uLzqb-16I','Owen','https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJDpPWt9YI3JVuRzWicJjxBgqiaNYSQRhXetFgBDdBh9b4VfGMLz65DccOGfbs6yloLajHeVs7vIZ1Q/132',1665165078659,1667943292388,0),('o3pH95TieWohNIh2r5tMmzFtfkKs','辛福','https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJ8RCepK6Kou1HwypLKlLghtXuDmMl0gDF26CCXvsy0Is8ROicYQAHjsvFh89eeReV4q7yKUkQyNdg/132',1664638761984,1664727353441,0),('o3pH95WbrvqNzUjrGH4tZWjX3t2A','Martin','https://thirdwx.qlogo.cn/mmopen/vi_32/vSEmz4AoRXiaCI1h0BZTqae9eciaDRYXPccrXIhtOY9IyP88DxS7UYo0ibUBecBxGUvsdiaxq5MAnjI5SIaCuyUO9Q/132',1665739235016,1665739235016,0),('o3pH95ZbmZSBAurN4X2-U3A3_Ckk','Reiki::Kao','https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoOwHXeYmiaiaRhQ6bX84qn7ZPJyrib77MwhYqR3icC8m1JS9w4DLmJF85VymjtzNymrVv3laCMT7uLUg/132',1664647940338,1664647940338,0),('o3pH95ZmNkbPak78stlEvOSoRwhA','10.11','https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI2uJCibV6qlssKfrCkKDE2ibmbjlNB7VicgZ8xDavOOnfz8afK9WQnfDibT2Nyca5tIr8G31eIajE8FA/132',1664724395507,1664724395507,0),('o3pH95ZvCWQbOtrIWDakZ1_n2P0o','Caesar','https://thirdwx.qlogo.cn/mmopen/vi_32/l3INibaPaD0nVMrn3dPGXD6nnib8p1FJxUhAjEcqibscyCVDw0Gz9JvcWSSc19tnLHXHAQY4icJ43CLQtwSA8sk1Ew/132',1664647991990,1664647991990,0),('o3pH95a-RkZZy3oX8ILUZPHeD1QM','Virukisu','https://thirdwx.qlogo.cn/mmopen/vi_32/9ZbIC7NmVziaGvp5DdB9Yd28OEUv1ibKyJicApgkmKAQXa43QYYVNR84ib9P8UmrBSLXkP2Wl6j3bk2ZHUwPMZ9SHw/132',1664647992399,1664647992399,0),('o3pH95byUNGnFlb7uC2I8rraUenU','搞学术的羊驼','https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKyMDiaCgCKgSIqLCv5T9CuzcOoMcSgLFgsYccssdblHdgao5NTIg5y4xBhYmFM6dh1HoFq9K2J7iaQ/132',1664660804695,1664660804695,0),('o3pH95ckAWvhe6a5J3p_u6H86XmE','快乐旅行','https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLOWucpCLACCaAiaiaFYjfW3ZXiciaFUdVumia2ehUfljShuJpGNMmPiaXsYRTSJwCeAibQBkPMP2UOAVxfw/132',1664767663421,1664767663421,0),('o3pH95dKOD4NFn6Euho3_ZeC4kRo','小猫头鹰?','https://thirdwx.qlogo.cn/mmopen/vi_32/x3cpRvAjkpwEB3YQmBaScGicJhT99owiczhwtlTyc1gxib15IYJxyTxuyRweAhW2Fd000sJicJoA5hKyZ2mIHSiaysA/132',1664650998242,1665732170407,0),('o3pH95dzAsxWjBvb3SR9CgdDAaGk','我不是我','https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erwLrPEc3Hp1AUkEM4rkIX9NXUhDVlHczBsQlcPbLMh36dERh2IAA1lRBvYJ08ibqG0gNz66QEhGCQ/132',1667139943777,1667139943777,0),('o3pH95eSirfYzUW2sP61ohzKgay4','zihuatanejo','https://thirdwx.qlogo.cn/mmopen/vi_32/gfnQyDo2iaD0KH1F9zJg3e0GhTVl93aTf3qfIRAKiczrNImXU3P58shxXibKeQEfVsWGrGW4C2NYLGgiaeib9fianVIg/132',1664636773438,1713076361406,0),('oVvsO7bNlqSos9Lm4JWhwO4W_hsA','微信用户','https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132',1713077875336,1713084158931,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_collection`
--

DROP TABLE IF EXISTS `user_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_collection` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `goods_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_collection`
--

LOCK TABLES `user_collection` WRITE;
/*!40000 ALTER TABLE `user_collection` DISABLE KEYS */;
INSERT INTO `user_collection` VALUES ('221002174426536542208','o3pH95SMXXjVDb0TN9hF2H6IGyQc','22100244352512458752',0,1664723173053),('22100302124027002880','o3pH95TieWohNIh2r5tMmzFtfkKs','22100244352512458752',0,1664727412815);
/*!40000 ALTER TABLE `user_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_wanted`
--

DROP TABLE IF EXISTS `user_wanted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_wanted` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `goods_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `is_cancelled` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_wanted`
--

LOCK TABLES `user_wanted` WRITE;
/*!40000 ALTER TABLE `user_wanted` DISABLE KEYS */;
INSERT INTO `user_wanted` VALUES ('221002174426500890624','o3pH95SMXXjVDb0TN9hF2H6IGyQc','22100244352512458752',0,1664723173037),('22100302123467063296','o3pH95TieWohNIh2r5tMmzFtfkKs','22100244352512458752',0,1664727412548),('22100386642731778048','o3pH95ckAWvhe6a5J3p_u6H86XmE','22100332858276102144',0,1664767714474),('22100812556796166144','o3pH95dKOD4NFn6Euho3_ZeC4kRo','22100244352512458752',0,1665164387547),('24041467767558471680','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','22100244352512458752',0,1713085114090),('24041467782555205632','oVvsO7bNlqSos9Lm4JWhwO4W_hsA','23101423625233072128',0,1713085121241);
/*!40000 ALTER TABLE `user_wanted` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-14  5:17:38
