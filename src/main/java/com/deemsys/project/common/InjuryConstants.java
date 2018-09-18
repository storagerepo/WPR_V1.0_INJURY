package com.deemsys.project.common;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.LocalDate;

public class InjuryConstants {
	
	
	public static Integer INJURY_SUPER_ADMIN_ROLE_ID=1;
	public static Integer INJURY_CALLER_ADMIN_ROLE_ID=2;
	public static Integer INJURY_LAWYER_ADMIN_ROLE_ID=3;
	public static Integer INJURY_CALLER_ROLE_ID=4;
	public static Integer INJURY_LAWYER_ROLE_ID=5;
	public static Integer INJURY_AUTO_MANAGER_ROLE_ID=6;
	public static Integer INJURY_AUTO_DEALER_ROLE_ID=7;
	public static Integer INJURY_BODY_SHOP_OWNER_ROLE_ID=8;
	public static Integer INJURY_SHOP_ROLE_ID=9;
	
	public static String INJURY_SUPER_ADMIN_ROLE="ROLE_SUPER_ADMIN";
	public static String INJURY_CALLER_ADMIN_ROLE="ROLE_CALLER_ADMIN";
	public static String INJURY_LAWYER_ADMIN_ROLE="ROLE_LAWYER_ADMIN";
	public static String INJURY_CALLER_ROLE="ROLE_CALLER";
	public static String INJURY_LAWYER_ROLE="ROLE_LAWYER";
	public static String INJURY_AUTO_MANAGER_ROLE="ROLE_AUTO_MANAGER";
	public static String INJURY_AUTO_DEALER_ROLE="ROLE_AUTO_DEALER";
	public static String INJURY_BODY_SHOP_OWNER_ROLE="ROLE_BODY_SHOP_OWNER";
	public static String INJURY_SHOP_ROLE="ROLE_SHOP";
	
	//Look up Preference Type
	public static Integer COUNTY_LOOKUP=1;
	public static Integer TIER_LOOKUP=2;
	public static Integer AGE_LOOKUP=3;
	public static Integer REPORTING_AGENCY_LOOKUP=4;
	
	// Reporting Agency Code Separator
	public static String REPORTING_AGENCY_CODE_SEPARATOR="-";
	public static String REPORTING_AGENCY_NAME_SEPARATOR="-";
	
	// Export Excel Type
	public static Integer PATIENT_EXPORT=1;
	public static Integer ACTVITY_LOG_EXPORT=2;
	
	//1st level scanned reports credentials
	//1st level scanned reports credentials
		public static String[][] MOTORIST_PAGE_COORDINATES_PARSER_ONE={
				{ "occupantOneUnitNumber", "occupantOneName", "occupantOneDateOfBirth",
					"occupantOnePatientAge", "occupantOneGender", "occupantOneAddress",
					"occupantOnePhoneNumber", "occupantOneInjury", "occupantOneEMSAgency",
					"occupantOneMedicalFacility", "occupantOneSeatingPosition" },
			{ "occupantTwoUnitNumber", "occupantTwoName", "occupantTwoDateOfBirth",
					"occupantTwoPatientAge", "occupantTwoGender", "occupantTwoAddress",
					"occupantTwoPhoneNumber", "occupantTwoInjury", "occupantTwoEMSAgency",
					"occupantTwoMedicalFacility", "occupantTwoSeatingPosition" },
			{ "occupantThreeUnitNumber", "occupantThreeName", "occupantThreeDateOfBirth",
					"occupantThreePatientAge", "occupantThreeGender", "occupantThreeAddress",
					"occupantThreePhoneNumber", "occupantThreeInjury", "occupantThreeEMSAgency",
					"occupantThreeMedicalFacility", "occupantThreeSeatingPosition" },
			{ "occupantFourUnitNumber", "occupantFourName", "occupantFourDateOfBirth",
					"occupantFourPatientAge", "occupantFourGender", "occupantFourAddress",
					"occupantFourPhoneNumber", "occupantFourInjury", "occupantFourEMSAgency",
					"occupantFourMedicalFacility", "occupantFourSeatingPosition" } 
					};
		public static String[][] OCCUPANT_PAGE_COORDINATES_PARSER_ONE={
				{ "occupantFiveUnitNumber", "occupantFiveName", "occupantFiveDateOfBirth",
					"occupantFivePatientAge", "occupantFiveGender", "occupantFiveAddress",
					"occupantFivePhoneNumber", "occupantFiveInjury", "occupantFiveEMSAgency",
					"occupantFiveMedicalFacility", "occupantFiveSeatingPosition" },
			{ "occupantSixUnitNumber", "occupantSixName", "occupantSixDateOfBirth",
					"occupantSixPatientAge", "occupantSixGender", "occupantSixAddress",
					"occupantSixPhoneNumber", "occupantSixInjury", "occupantSixEMSAgency",
					"occupantSixMedicalFacility", "occupantSixSeatingPosition" },
			{ "occupantSevenUnitNumber", "occupantSevenName", "occupantSevenDateOfBirth",
					"occupantSevenPatientAge", "occupantSevenGender", "occupantSevenAddress",
					"occupantSevenPhoneNumber", "occupantSevenInjury", "occupantSevenEMSAgency",
					"occupantSevenMedicalFacility", "occupantSevenSeatingPosition" },
			{ "occupantEightUnitNumber", "occupantEightName", "occupantEightDateOfBirth",
					"occupantEightPatientAge", "occupantEightGender", "occupantEightAddress",
					"occupantEightPhoneNumber", "occupantEightInjury", "occupantEightEMSAgency",
					"occupantEightMedicalFacility", "occupantEightSeatingPosition" },
			{ "occupantNineUnitNumber", "occupantNineName", "occupantNineDateOfBirth",
					"occupantNinePatientAge", "occupantNineGender", "occupantNineAddress",
					"occupantNinePhoneNumber", "occupantNineInjury", "occupantNineEMSAgency",
					"occupantNineMedicalFacility", "occupantNineSeatingPosition" },
			{ "occupantTenUnitNumber", "occupantTenName", "occupantTenDateOfBirth",
					"occupantTenPatientAge", "occupantTenGender", "occupantTenAddress",
					"occupantTenPhoneNumber", "occupantTenInjury", "occupantTenEMSAgency",
					"occupantTenMedicalFacility", "occupantTenSeatingPosition" } };
		//second level reports coordinates
	public static String[][] MOTORIST_PAGE_COORDINATES_PARSER_TWO={
			{ "occupantOneUnitNumber", "occupantOnePatientName", "occupantOneDateOfBirth",
				"occupantOnePatientAge", "occupantOneGender", "occupantOneAddressDoorNo",
				"occupantOneAddressStreetName","occupantOneAddressCity","occupantOneAddressState",
				"occupantOnePhoneNumber", "occupantOneInjury", "occupantOneEMSAgency",
				"occupantOneMedicalFacility", "occupantOneSeatingPosition" },
		{ "occupantTwoUnitNumber", "occupantTwoPatientName", "occupantTwoDateOfBirth",
				"occupantTwoPatientAge", "occupantTwoGender","occupantTwoAddressDoorNo",
				"occupantTwoAddressStreetName","occupantTwoAddressCity","occupantTwoAddressState",
				"occupantTwoPhoneNumber", "occupantTwoInjury", "occupantTwoEMSAgency",
				"occupantTwoMedicalFacility", "occupantTwoSeatingPosition" },
		{ "occupantThreeUnitNumber", "occupantThreePatientName", "occupantThreeDateOfBirth",
				"occupantThreePatientAge", "occupantThreeGender","occupantThreeAddressDoorNo",
				"occupantThreeAddressStreetName","occupantThreeAddressCity","occupantThreeAddressState",
				"occupantThreePhoneNumber", "occupantThreeInjury", "occupantThreeEMSAgency",
				"occupantThreeMedicalFacility", "occupantThreeSeatingPosition" },
		{ "occupantFourUnitNumber", "occupantFourPatientName", "occupantFourDateOfBirth",
				"occupantFourPatientAge", "occupantFourGender","occupantFourAddressDoorNo",
				"occupantFourAddressStreetName","occupantFourAddressCity","occupantFourAddressState",
				"occupantFourPhoneNumber", "occupantFourInjury", "occupantFourEMSAgency",
				"occupantFourMedicalFacility", "occupantFourSeatingPosition" } 
				};
	public static String[][] OCCUPANT_PAGE_COORDINATES_PARSER_TWO={
			{ "occupantFiveUnitNumber", "occupantFivePatientName", "occupantFiveDateOfBirth",
				"occupantFivePatientAge", "occupantFiveGender","occupantFiveAddressDoorNo",
				"occupantFiveAddressStreetName","occupantFiveAddressCity","occupantFiveAddressState",
				"occupantFivePhoneNumber", "occupantFiveInjury", "occupantFiveEMSAgency",
				"occupantFiveMedicalFacility", "occupantFiveSeatingPosition" },
		{ "occupantSixUnitNumber", "occupantSixPatientName", "occupantSixDateOfBirth",
				"occupantSixPatientAge", "occupantSixGender","occupantSixAddressDoorNo",
				"occupantSixAddressStreetName","occupantSixAddressCity","occupantSixAddressState",
				"occupantSixPhoneNumber", "occupantSixInjury", "occupantSixEMSAgency",
				"occupantSixMedicalFacility", "occupantSixSeatingPosition" },
		{ "occupantSevenUnitNumber", "occupantSevenPatientName", "occupantSevenDateOfBirth",
				"occupantSevenPatientAge", "occupantSevenGender","occupantSevenAddressDoorNo",
				"occupantSevenAddressStreetName","occupantSevenAddressCity","occupantSevenAddressState",
				"occupantSevenPhoneNumber", "occupantSevenInjury", "occupantSevenEMSAgency",
				"occupantSevenMedicalFacility", "occupantSevenSeatingPosition" },
		{ "occupantEightUnitNumber", "occupantEightPatientName", "occupantEightDateOfBirth",
				"occupantEightPatientAge", "occupantEightGender","occupantEightAddressDoorNo",
				"occupantEightAddressStreetName","occupantEightAddressCity","occupantEightAddressState",
				"occupantEightPhoneNumber", "occupantEightInjury", "occupantEightEMSAgency",
				"occupantEightMedicalFacility", "occupantEightSeatingPosition" },
		{ "occupantNineUnitNumber", "occupantNinePatientName", "occupantNineDateOfBirth",
				"occupantNinePatientAge", "occupantNineGender","occupantNineAddressDoorNo",
				"occupantNineAddressStreetName","occupantNineAddressCity","occupantNineAddressState",
				"occupantNinePhoneNumber", "occupantNineInjury", "occupantNineEMSAgency",
				"occupantNineMedicalFacility", "occupantNineSeatingPosition" },
		{ "occupantTenUnitNumber", "occupantTenPatientName", "occupantTenDateOfBirth",
				"occupantTenPatientAge", "occupantTenGender","occupantTenAddressDoorNo",
				"occupantTenAddressStreetName","occupantTenAddressCity","occupantTenAddressState",
				"occupantTenPhoneNumber", "occupantTenInjury", "occupantTenEMSAgency",
				"occupantTenMedicalFacility", "occupantTenSeatingPosition" } };
	
	//Default street short forms
	public static List<String> getDefaultStreetForms() {
		List<String> defaultStreetForms = new ArrayList<String>();
		defaultStreetForms.add("ALLEE");
		defaultStreetForms.add("ALLEY");
		defaultStreetForms.add("ALLY");
		defaultStreetForms.add("ALY");
		defaultStreetForms.add("ANEX");
		defaultStreetForms.add("ANNEX");
		defaultStreetForms.add("ANNX");
		defaultStreetForms.add("ANX");
		defaultStreetForms.add("ARC");
		defaultStreetForms.add("ARCADE");
		defaultStreetForms.add("AV");
		defaultStreetForms.add("AVE");
		defaultStreetForms.add("AVEN");
		defaultStreetForms.add("AVENU");
		defaultStreetForms.add("AVENUE");
		defaultStreetForms.add("AVN");
		defaultStreetForms.add("AVNUE");
		defaultStreetForms.add("BAYOO");
		defaultStreetForms.add("BAYOU");
		defaultStreetForms.add("BYU");
		defaultStreetForms.add("BCH");
		defaultStreetForms.add("BEACH");
		defaultStreetForms.add("BEND");
		defaultStreetForms.add("BND");
		defaultStreetForms.add("BLF");
		defaultStreetForms.add("BLUF");
		defaultStreetForms.add("BLUFF");
		defaultStreetForms.add("BLUFFS");
		defaultStreetForms.add("BOT");
		defaultStreetForms.add("BTM");
		defaultStreetForms.add("BOTTM");
		defaultStreetForms.add("BOTTOM");
		defaultStreetForms.add("BLVD");
		defaultStreetForms.add("BOUL");
		defaultStreetForms.add("BOULEVARD");
		defaultStreetForms.add("BOULV");
		defaultStreetForms.add("BR");
		defaultStreetForms.add("BRNCH");
		defaultStreetForms.add("BRANCH");
		defaultStreetForms.add("BRDGE");
		defaultStreetForms.add("BRG");
		defaultStreetForms.add("BRIDGE");
		defaultStreetForms.add("BRK");
		defaultStreetForms.add("BROOK");
		defaultStreetForms.add("BROOKS");
		defaultStreetForms.add("BRKS");
		defaultStreetForms.add("BURG");
		defaultStreetForms.add("BG");
		defaultStreetForms.add("BURGS");
		defaultStreetForms.add("BGS");
		defaultStreetForms.add("BYP");
		defaultStreetForms.add("BYPA");
		defaultStreetForms.add("BYPAS");
		defaultStreetForms.add("BYPAS");
		defaultStreetForms.add("BYPASS");
		defaultStreetForms.add("BYPS");
		defaultStreetForms.add("BYPS");
		defaultStreetForms.add("CAMP");
		defaultStreetForms.add("CMP");
		defaultStreetForms.add("CANYN");
		defaultStreetForms.add("CANYON");
		defaultStreetForms.add("CNYN");
		defaultStreetForms.add("CAPE");
		defaultStreetForms.add("CPE");
		defaultStreetForms.add("CAUSEWAY");
		defaultStreetForms.add("CAUSWA");
		defaultStreetForms.add("CSWY");
		defaultStreetForms.add("CEN");
		defaultStreetForms.add("CENT");
		defaultStreetForms.add("CENTER");
		defaultStreetForms.add("CENTR");
		defaultStreetForms.add("CENTRE");
		defaultStreetForms.add("CNTER");
		defaultStreetForms.add("CNTR");
		defaultStreetForms.add("CTR");
		defaultStreetForms.add("CENTERS");
		defaultStreetForms.add("CIR");
		defaultStreetForms.add("CIRC");
		defaultStreetForms.add("CIRCL");
		defaultStreetForms.add("CIRCLE");
		defaultStreetForms.add("CRCL");
		defaultStreetForms.add("CRCLE");
		defaultStreetForms.add("CIRCLES");
		defaultStreetForms.add("CLF");
		defaultStreetForms.add("CLIFF");
		defaultStreetForms.add("CLFS");
		defaultStreetForms.add("CLIFFS");
		defaultStreetForms.add("CLB");
		defaultStreetForms.add("CLUB");
		defaultStreetForms.add("COMMON");
		defaultStreetForms.add("COMMONS");
		defaultStreetForms.add("COR");
		defaultStreetForms.add("CORNER");
		defaultStreetForms.add("CORNERS");
		defaultStreetForms.add("CORS");
		defaultStreetForms.add("COURSE");
		defaultStreetForms.add("CRSE");
		defaultStreetForms.add("COURT");
		defaultStreetForms.add("CT");
		defaultStreetForms.add("COURTS");
		defaultStreetForms.add("CTS");
		defaultStreetForms.add("COVE");
		defaultStreetForms.add("CV");
		defaultStreetForms.add("COVES");
		defaultStreetForms.add("CREEK");
		defaultStreetForms.add("CRK");
		defaultStreetForms.add("CRESCENT");
		defaultStreetForms.add("CRES");
		defaultStreetForms.add("CRSENT");
		defaultStreetForms.add("CRSNT");
		defaultStreetForms.add("CREST");
		defaultStreetForms.add("CROSSING");
		defaultStreetForms.add("CRSSNG");
		defaultStreetForms.add("XING");
		defaultStreetForms.add("CROSSROAD");
		defaultStreetForms.add("CROSSROADS");
		defaultStreetForms.add("CURVE");
		defaultStreetForms.add("DALE");
		defaultStreetForms.add("DL");
		defaultStreetForms.add("DAM");
		defaultStreetForms.add("DM");
		//divide
		defaultStreetForms.add("DIV");
		defaultStreetForms.add("DIVIDE");
		defaultStreetForms.add("DV");
		defaultStreetForms.add("DVD");
		//Drive
		defaultStreetForms.add("DR");
		defaultStreetForms.add("DRIV");
		defaultStreetForms.add("DRIVE");
		defaultStreetForms.add("DRV");
		//Drives
		defaultStreetForms.add("DRIVES");
		defaultStreetForms.add("DRS");
		defaultStreetForms.add("ESTATE");
		defaultStreetForms.add("EST");
		defaultStreetForms.add("ESTATES");
		defaultStreetForms.add("ESTS");
		defaultStreetForms.add("EXP");
		defaultStreetForms.add("EXPR");
		defaultStreetForms.add("EXPRESS");
		defaultStreetForms.add("EXPRESSWAY");
		defaultStreetForms.add("EXPW");
		defaultStreetForms.add("EXPY");
		defaultStreetForms.add("EXT");
		defaultStreetForms.add("EXTENSION");
		defaultStreetForms.add("EXTN");
		defaultStreetForms.add("EXTNSN");
		defaultStreetForms.add("EXTS");
		defaultStreetForms.add("FALL");
		defaultStreetForms.add("FALLS");
		defaultStreetForms.add("FLS");
		defaultStreetForms.add("FERRY");
		defaultStreetForms.add("FRRY");
		defaultStreetForms.add("FRY");
		defaultStreetForms.add("FIELD");
		defaultStreetForms.add("FLD");
		defaultStreetForms.add("FIELDS");
		defaultStreetForms.add("FLDS");
		defaultStreetForms.add("FLAT");
		defaultStreetForms.add("FLT");
		defaultStreetForms.add("FLATS");
		defaultStreetForms.add("FLTS");
		defaultStreetForms.add("FORD");
		defaultStreetForms.add("FRD");
		defaultStreetForms.add("FORDS");
		defaultStreetForms.add("FOREST");
		defaultStreetForms.add("FORESTS");
		defaultStreetForms.add("FRST");
		defaultStreetForms.add("FORG");
		defaultStreetForms.add("FORGE");
		defaultStreetForms.add("FRG");
		defaultStreetForms.add("FORGES");
		defaultStreetForms.add("FORK");
		defaultStreetForms.add("FRK");
		defaultStreetForms.add("FORKS");
		defaultStreetForms.add("FRKS");
		defaultStreetForms.add("FORT");
		defaultStreetForms.add("FRT");
		defaultStreetForms.add("FT");
		defaultStreetForms.add("FREEWAY");
		defaultStreetForms.add("FREEWY");
		defaultStreetForms.add("FRWAY");
		defaultStreetForms.add("FRWY");
		defaultStreetForms.add("FWY");
		defaultStreetForms.add("GARDEN");
		defaultStreetForms.add("GARDN");
		defaultStreetForms.add("GRDEN");
		defaultStreetForms.add("GRDN");
		defaultStreetForms.add("GARDENS");
		defaultStreetForms.add("GDNS");
		defaultStreetForms.add("GRDNS");
		defaultStreetForms.add("GATEWAY");
		defaultStreetForms.add("GATEWY");
		defaultStreetForms.add("GATWAY");
		defaultStreetForms.add("GTWAY");
		defaultStreetForms.add("GTWY");
		defaultStreetForms.add("GLEN");
		defaultStreetForms.add("GLN");
		defaultStreetForms.add("GLENS");
		defaultStreetForms.add("GREEN");
		defaultStreetForms.add("GRN");
		defaultStreetForms.add("GREENS");
		defaultStreetForms.add("GROV");
		defaultStreetForms.add("GROVE");
		defaultStreetForms.add("GRV");
		defaultStreetForms.add("GROVES");
		defaultStreetForms.add("HARBOR");
		defaultStreetForms.add("HARBR");
		defaultStreetForms.add("HBR");
		defaultStreetForms.add("HRBOR");
		defaultStreetForms.add("HARBORS");
		defaultStreetForms.add("HAVEN");
		defaultStreetForms.add("HVN");
		//Heights
		defaultStreetForms.add("HT");
		defaultStreetForms.add("HTS");
		//Highway
		defaultStreetForms.add("HIGHWAY");
		defaultStreetForms.add("HIGHWY");
		defaultStreetForms.add("HIWAY");
		defaultStreetForms.add("HIWY");
		defaultStreetForms.add("HWAY");
		defaultStreetForms.add("HWY");
		//Hill
		defaultStreetForms.add("HILL");
		defaultStreetForms.add("HL");
		//HILLS
		defaultStreetForms.add("HILLS");
		defaultStreetForms.add("HLS");
		defaultStreetForms.add("HLLW");
		defaultStreetForms.add("HOLLOW");
		defaultStreetForms.add("HOLLOWS");
		defaultStreetForms.add("HOLW");
		defaultStreetForms.add("HOLWS");
		defaultStreetForms.add("INLT");
		//ISLAND
		defaultStreetForms.add("IS");
		defaultStreetForms.add("ISLAND");
		defaultStreetForms.add("ISLND");
		//Islands
		defaultStreetForms.add("ISLANDS");
		defaultStreetForms.add("ISLNDS");
		defaultStreetForms.add("ISS");
		defaultStreetForms.add("ISLE");
		defaultStreetForms.add("ISLES");
		//Junction
		defaultStreetForms.add("JCT");
		defaultStreetForms.add("JCTION");
		defaultStreetForms.add("JCTN");
		defaultStreetForms.add("JUNCTION");
		defaultStreetForms.add("JUNCTN");
		defaultStreetForms.add("JUNCTON");
		//Junctions
		defaultStreetForms.add("JCTNS");
		defaultStreetForms.add("JCTS");
		defaultStreetForms.add("JUNCTIONS");
		defaultStreetForms.add("KEY");
		defaultStreetForms.add("KY");
		defaultStreetForms.add("KEYS");
		defaultStreetForms.add("KYS");
		defaultStreetForms.add("KNL");
		defaultStreetForms.add("KNOL");
		defaultStreetForms.add("KNOLL");
		defaultStreetForms.add("KNLS");
		defaultStreetForms.add("KNOLLS");
		//Lake
		defaultStreetForms.add("LK");
		defaultStreetForms.add("LAKE");
		//Lakes
		defaultStreetForms.add("LKS");
		defaultStreetForms.add("LAKES");
		defaultStreetForms.add("LAND");
		defaultStreetForms.add("LANDING");
		defaultStreetForms.add("LNDG");
		defaultStreetForms.add("LNDNG");
		//LANE
		defaultStreetForms.add("LANE");
		defaultStreetForms.add("LN");
		defaultStreetForms.add("LGT");
		defaultStreetForms.add("LIGHT");
		defaultStreetForms.add("LIGHTS");
		defaultStreetForms.add("LF");
		defaultStreetForms.add("LOAF");
		defaultStreetForms.add("LCK");
		defaultStreetForms.add("LOCK");
		defaultStreetForms.add("LCKS");
		defaultStreetForms.add("LOCKS");
		defaultStreetForms.add("LDG");
		defaultStreetForms.add("LDGE");
		defaultStreetForms.add("LODG");
		defaultStreetForms.add("LODGE");
		defaultStreetForms.add("LOOP");
		defaultStreetForms.add("LOOPS");
		defaultStreetForms.add("MALL");
		defaultStreetForms.add("MNR");
		defaultStreetForms.add("MANOR");
		defaultStreetForms.add("MANORS");
		defaultStreetForms.add("MNRS");
		defaultStreetForms.add("MEADOW");
		defaultStreetForms.add("MDW");
		defaultStreetForms.add("MDWS");
		defaultStreetForms.add("MEADOWS");
		defaultStreetForms.add("MEDOWS");
		defaultStreetForms.add("MEWS");
		defaultStreetForms.add("MILL");
		defaultStreetForms.add("MILLS");
		defaultStreetForms.add("MISSN");
		defaultStreetForms.add("MSSN");
		//Motorway
		defaultStreetForms.add("MOTORWAY");
		defaultStreetForms.add("MTWY");
		//Mount
		defaultStreetForms.add("MNT");
		defaultStreetForms.add("MT");
		defaultStreetForms.add("MOUNT");
		//Mountain
		defaultStreetForms.add("MNTAIN");
		defaultStreetForms.add("MNTN");
		defaultStreetForms.add("MOUNTAIN");
		defaultStreetForms.add("MOUNTIN");
		defaultStreetForms.add("MTIN");
		defaultStreetForms.add("MTN");
		defaultStreetForms.add("MNTNS");
		defaultStreetForms.add("MOUNTAINS");
		defaultStreetForms.add("NCK");
		defaultStreetForms.add("NECK");
		defaultStreetForms.add("ORCH");
		defaultStreetForms.add("ORCHARD");
		defaultStreetForms.add("ORCHRD");
		defaultStreetForms.add("OVAL");
		defaultStreetForms.add("OVL");
		defaultStreetForms.add("OVERPASS");
		//PARK
		defaultStreetForms.add("PARK");
		defaultStreetForms.add("PRK");
		defaultStreetForms.add("PARKS");
		//Parkway
		defaultStreetForms.add("PARKWAY");
		defaultStreetForms.add("PARKWY");
		defaultStreetForms.add("PKWAY");
		defaultStreetForms.add("PKWY");
		defaultStreetForms.add("PKY");
		//PARKWAYS
		defaultStreetForms.add("PARKWAYS");
		defaultStreetForms.add("PKWYS");
		//Pass
		defaultStreetForms.add("PASS");
		//Passage
		defaultStreetForms.add("PASSAGE");
		defaultStreetForms.add("PSGE");
		//PATH
		defaultStreetForms.add("PATH");
		defaultStreetForms.add("PATHS");
		defaultStreetForms.add("PIKE");
		defaultStreetForms.add("PIKES");
		defaultStreetForms.add("PINE");
		defaultStreetForms.add("PINES");
		defaultStreetForms.add("PNES");
		defaultStreetForms.add("PL");
		defaultStreetForms.add("PLAIN");
		defaultStreetForms.add("PLN");
		defaultStreetForms.add("PLAINS");
		defaultStreetForms.add("PLNS");
		defaultStreetForms.add("PLAZA");
		defaultStreetForms.add("PLZ");
		defaultStreetForms.add("PLZA");
		//Point
		defaultStreetForms.add("POINT");
		defaultStreetForms.add("PT");
		//Points
		defaultStreetForms.add("POINTS");
		defaultStreetForms.add("PTS");
		//PORT
		defaultStreetForms.add("PORT");
		defaultStreetForms.add("PRT");
		//PORTS
		defaultStreetForms.add("PORTS");
		defaultStreetForms.add("PRTS");
		defaultStreetForms.add("PR");
		defaultStreetForms.add("PRAIRIE");
		defaultStreetForms.add("PRR");
		defaultStreetForms.add("RAD");
		defaultStreetForms.add("RADIAL");
		defaultStreetForms.add("RADIEL");
		defaultStreetForms.add("RADL");
		defaultStreetForms.add("RAMP");
		defaultStreetForms.add("RANCHES");
		defaultStreetForms.add("RANCH");
		defaultStreetForms.add("RNCH");
		defaultStreetForms.add("RNCHS");
		defaultStreetForms.add("RAPID");
		defaultStreetForms.add("RPD");
		defaultStreetForms.add("RAPIDS");
		defaultStreetForms.add("RPDS");
		defaultStreetForms.add("REST");
		defaultStreetForms.add("RST");
		defaultStreetForms.add("RDG");
		defaultStreetForms.add("RDGE");
		defaultStreetForms.add("RIDGE");
		defaultStreetForms.add("RDGS");
		defaultStreetForms.add("RIDGES");
		//River
		defaultStreetForms.add("RIV");
		defaultStreetForms.add("RIVER");
		defaultStreetForms.add("RVR");
		defaultStreetForms.add("RIVR");
		//Road
		defaultStreetForms.add("RD");
		defaultStreetForms.add("ROAD");
		defaultStreetForms.add("ROADS");
		defaultStreetForms.add("RDS");
		defaultStreetForms.add("ROUTE");
		defaultStreetForms.add("ROW");
		defaultStreetForms.add("RUE");
		defaultStreetForms.add("RUN");
		defaultStreetForms.add("SHL");
		defaultStreetForms.add("SHOAL");
		defaultStreetForms.add("SHLS");
		defaultStreetForms.add("SHOALS");
		defaultStreetForms.add("SHOAR");
		defaultStreetForms.add("SHORE");
		defaultStreetForms.add("SHR");
		defaultStreetForms.add("SHOARS");
		defaultStreetForms.add("SHORES");
		defaultStreetForms.add("SHRS");
		defaultStreetForms.add("SKYWAY");
		defaultStreetForms.add("SPG");
		defaultStreetForms.add("SPNG");
		defaultStreetForms.add("SPRING");
		defaultStreetForms.add("SPRNG");
		defaultStreetForms.add("SPGS");
		defaultStreetForms.add("SPNGS");
		defaultStreetForms.add("SPRINGS");
		defaultStreetForms.add("SPRNGS");
		defaultStreetForms.add("SPUR");
		defaultStreetForms.add("SPURS");
		defaultStreetForms.add("SPRINGS");
		defaultStreetForms.add("SQ");
		defaultStreetForms.add("SQR");
		defaultStreetForms.add("SQRE");
		defaultStreetForms.add("SQU");
		defaultStreetForms.add("SQUARE");
		defaultStreetForms.add("SQRS");
		defaultStreetForms.add("SQUARES");
		defaultStreetForms.add("STA");
		defaultStreetForms.add("STATION");
		defaultStreetForms.add("STATN");
		defaultStreetForms.add("STN");
		defaultStreetForms.add("STRA");
		defaultStreetForms.add("STRAV");
		defaultStreetForms.add("STRAVEN");
		defaultStreetForms.add("STRAVENUE");
		defaultStreetForms.add("STRAVN");
		defaultStreetForms.add("STRVN");
		defaultStreetForms.add("STRVNUE");
		defaultStreetForms.add("STREAM");
		defaultStreetForms.add("STREME");
		defaultStreetForms.add("STRM");
		defaultStreetForms.add("SMT");
		defaultStreetForms.add("SUMIT");
		defaultStreetForms.add("SUMITT");
		defaultStreetForms.add("SUMMIT");
		//STREET
		defaultStreetForms.add("STREET");
		defaultStreetForms.add("STRT");
		defaultStreetForms.add("ST");
		defaultStreetForms.add("STR");
		defaultStreetForms.add("STREETS");
		defaultStreetForms.add("STS");
		defaultStreetForms.add("TER");
		defaultStreetForms.add("TERR");
		defaultStreetForms.add("TERRACE");
		defaultStreetForms.add("THROUGHWAY");
		defaultStreetForms.add("TRACE");
		defaultStreetForms.add("TRACES");
		defaultStreetForms.add("TRCE");
		defaultStreetForms.add("TRACK");
		defaultStreetForms.add("TRACKS");
		defaultStreetForms.add("TRAK");
		defaultStreetForms.add("TRK");
		defaultStreetForms.add("TRKS");
		defaultStreetForms.add("TRAFFICWAY");
		defaultStreetForms.add("TRAIL");
		defaultStreetForms.add("TRAILS");
		defaultStreetForms.add("TRL");
		defaultStreetForms.add("TRLS");
		defaultStreetForms.add("TRAILER");
		defaultStreetForms.add("TRLR");
		defaultStreetForms.add("TRLRS");
		defaultStreetForms.add("TUNEL");
		defaultStreetForms.add("TUNL");
		defaultStreetForms.add("TUNLS");
		defaultStreetForms.add("TUNNEL");
		defaultStreetForms.add("TUNNELS");
		defaultStreetForms.add("TUNNL");
		defaultStreetForms.add("TRNPK");
		defaultStreetForms.add("TURNPIKE");
		defaultStreetForms.add("TURNPK");
		defaultStreetForms.add("UNDERPASS");
		defaultStreetForms.add("UN");
		defaultStreetForms.add("UNION");
		defaultStreetForms.add("UNIONS");
		defaultStreetForms.add("VALLEY");
		defaultStreetForms.add("VALLY");
		defaultStreetForms.add("VLLY");
		defaultStreetForms.add("VLY");
		defaultStreetForms.add("VALLEYS");
		defaultStreetForms.add("VLYS");
		defaultStreetForms.add("VDCT");
		defaultStreetForms.add("VIA");
		defaultStreetForms.add("VIADCT");
		defaultStreetForms.add("VIADUCT");
		defaultStreetForms.add("VW");
		defaultStreetForms.add("VIEWS");
		defaultStreetForms.add("VWS");
		defaultStreetForms.add("VILL");
		defaultStreetForms.add("VILLAG");
		defaultStreetForms.add("VILLAGE");
		defaultStreetForms.add("VILLG");
		defaultStreetForms.add("VILLIAGE");
		defaultStreetForms.add("VLG");
		defaultStreetForms.add("VILLAGES");
		defaultStreetForms.add("VLGS");
		defaultStreetForms.add("VILLE");
		defaultStreetForms.add("VL");
		defaultStreetForms.add("VIS");
		defaultStreetForms.add("VIST");
		defaultStreetForms.add("VISTA");
		defaultStreetForms.add("VST");
		defaultStreetForms.add("VSTA");
		defaultStreetForms.add("WALL");
		defaultStreetForms.add("WY");
		defaultStreetForms.add("WAY");
		defaultStreetForms.add("WAYS");
		defaultStreetForms.add("WELL");
		defaultStreetForms.add("WELLS");
		defaultStreetForms.add("WLS");
		//Walk
		defaultStreetForms.add("WALK");
		defaultStreetForms.add("WALKS");
		return defaultStreetForms;
	}
	// Convert Date To Year Format
	public static Date convertYearFormat(String date)
	{   SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateformat=new Date();
		try {
			dateformat = monthFormat.parse(date);
			dateformat=yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}
	
	// Convert Date To Year Format
	public static Date convertDateFromDateAndTime(String date)
	{   SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateformat=new Date();
		try {
			dateformat = monthFormat.parse(date);
			dateformat=yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}
	
	// Convert DateTime to Date Format
	public static Date convertDateTimetoDateFormat(Date date)
	{
	SimpleDateFormat DateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy-MM-dd");

		Date dateformat=new Date();
		try {
			dateformat = DateTimeFormat.parse(DateTimeFormat.format(date));
			dateformat=DateFormat.parse(DateFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}
	
	// Convert Date to USA Date Format
	public static Date convertDatetoUSDateFormat(Date date) {
		SimpleDateFormat DateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");

		Date dateformat = new Date();
		try {
			dateformat = DateTimeFormat.parse(DateTimeFormat.format(date));
			dateformat = DateFormat.parse(DateFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}

	// Convert To 24HourTime
	public static String convert24HourTime(String time) {
		String time24 = "";
		try {

			SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
			SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
			time24 = outFormat.format(inFormat.parse(time));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time24;

	}

	// Convert To AMPMTime
	public static String convertAMPMTime(String time) {
		String time24 = "";
		try {
			SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm aa");
			time24 = outFormat.format(inFormat.parse(time));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time24;

	}

	// Convert To Month Format
	public static String convertMonthFormat(Date date) {
		String convertedDate = "";
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateformat = new Date();
		try {
			if (date != null) {
				dateformat = yearFormat.parse(yearFormat.format(date));
				convertedDate = monthFormat.format(dateformat);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;
	}

	// Convert To USA Month Format With Time
	public static String convertUSAFormatWithTime(Date date) {
		SimpleDateFormat monthFormat = new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat yearFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss aa");
		Date dateformat = new Date();
		try {
			dateformat = yearFormat.parse(yearFormat.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return monthFormat.format(dateformat);
	}

	// Convert To Year Format With Time
	public static Date convertYearFormatWithTime(String date) {
		SimpleDateFormat monthFormat = new SimpleDateFormat(
				"MM/dd/yyyy hh:mm:ss aa");
		SimpleDateFormat yearFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date dateformat = new Date();
		try {
			dateformat = monthFormat.parse(date);
			dateformat = yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}
	
	// Convert To Year Format With Time By Date time
	public static Date convertYearFormatWithTimeByDate(String date) {
		SimpleDateFormat monthFormat = new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat yearFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date dateformat = new Date();
		try {
			dateformat = monthFormat.parse(date);
			dateformat = yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}

	// Get Current Date Time
	public static String getCurrentDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return sdf.format(new Date());
	}
	
	
	// Convert To USA Month Format With Time
	public static String convertUSAFormatWithTimeAMPM(String date) {
		String convertedDateTime = "";
		SimpleDateFormat monthFormat = new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat frontMonthFormat = new SimpleDateFormat(
				"MM/dd/yyyy hh:mm:ss aa");
		Date dateformat = new Date();
		try {
			if (date != null && !date.equals("")) {
				dateformat = monthFormat.parse(date);
				convertedDateTime = frontMonthFormat.format(dateformat);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDateTime;
	}

	// Convert String Time To Date Format Time
	public static Date convertToDateFormatTime(String time) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat yearFormat = new SimpleDateFormat("HH:mm");
		Date dateformat = new Date();
		try {
			dateformat = monthFormat.parse(time);
			dateformat = yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}

	// Convert Date Time To String Format Time
	public static String convertToStringFormatTime(Date time) {
		SimpleDateFormat yearFormat = new SimpleDateFormat("HH:mm");
		String dateformat = "";
		try {
			dateformat = yearFormat.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateformat;
	}

	// Convert String Time To Date Format Time
	public static Date convertToDateFormatTimeAMPM(String time) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat yearFormat = new SimpleDateFormat("hh:mm a");
		Date dateformat = new Date();
		try {
			dateformat = monthFormat.parse(time);
			dateformat = yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}

	// Convert Date Time To String Time
	public static String convertToStringFormatTimeAm(Date time) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("hh:mm a");
		String convertedTime = "";
		try {
			convertedTime = monthFormat.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertedTime;
	}

	// Get To Date By adding Number Of Days to From Date
	public static String getToDateByAddingNumberOfDays(String fromDate,
			Integer numberOfDays) {
		String toDate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date convertedDate = dateFormat.parse(fromDate);
			LocalDate fromLocalDate = new LocalDate(convertedDate);
			fromLocalDate = fromLocalDate.plusDays(numberOfDays - 1);
			toDate = dateFormat.format(fromLocalDate.toDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toDate;
	}

	public static List<String> getInvalidInsurance() {
		List<String> invalidInsurance = new ArrayList<String>();
		invalidInsurance.add("none");
		invalidInsurance.add("no insurance");
		invalidInsurance.add("n/a");
		invalidInsurance.add("na");
		invalidInsurance.add("not shown fra");
		invalidInsurance.add("none shown");
		invalidInsurance.add("fra not shown");
		invalidInsurance.add("none listed");
		invalidInsurance.add("fr not shown");
		invalidInsurance.add("not shown");
		invalidInsurance.add("no fr shown");
		invalidInsurance.add("unknown");
		invalidInsurance.add("-");
		return invalidInsurance;
	}

	// Generate Random String with Alpha For Password
	public String genereateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String pwd = RandomStringUtils.random(15, 0, 0, false, false,
				characters.toCharArray(), new SecureRandom());
		return pwd;
	}

	public static String getRoleAsText(String currentRole) {
		// TODO Auto-generated method stub
		String roleText = "";
		if (currentRole.equals(INJURY_SUPER_ADMIN_ROLE)) {
			roleText = "CRO Super Admin";
		} else if (currentRole.equals(INJURY_CALLER_ADMIN_ROLE)) {
			roleText = "Caller Admin";
		} else if (currentRole.equals(INJURY_CALLER_ROLE)) {
			roleText = "Caller";
		} else if (currentRole.equals(INJURY_LAWYER_ADMIN_ROLE)) {
			roleText = "Lawyer Admin";
		} else if (currentRole.equals(INJURY_LAWYER_ROLE)) {
			roleText = "Lawyer";
		} else if (currentRole.equals(INJURY_AUTO_MANAGER_ROLE)) {
			roleText = "Dealer Manager";
		} else if (currentRole.equals(INJURY_AUTO_DEALER_ROLE)) {
			roleText = "Dealer";
		} else if (currentRole.equals(INJURY_BODY_SHOP_OWNER_ROLE)) {
			roleText = "Body Shop Owner";
		} else if (currentRole.equals(INJURY_SHOP_ROLE)) {
			roleText = "Shop";
		}
		
		return roleText;
	}

	// convert Bigdecimal to Double
	public static double convertBigDecimaltoDouble(BigDecimal value) {
		double convertedValue = 0;
		if (value != null) {
			convertedValue = value.doubleValue();
		}
		return convertedValue;
	}

	// Convert Double to BigDecimal
	public static BigDecimal convertDoubleBigDecimal(Double value) {
		BigDecimal convertedValue = new BigDecimal(0);
		if (value != null) {
			convertedValue = new BigDecimal(value);
		}
		return convertedValue;
	}

	// Split Name By comma
	public static String[] splitNameByComma(String inputName) {
		String[] resultName = new String[] { "", "", "" };
		if (inputName != null) {
			String[] splittedName = inputName.split(",");
			if (splittedName.length == 2) {
				// Last Name
				resultName[0] = splittedName[0].trim();
				// First Name
				resultName[1] = splittedName[1].trim();
			} else if (splittedName.length == 1) {
				// First Name
				resultName[0] = splittedName[0].trim();
			} else if (splittedName.length == 3) {
				// Last Name
				resultName[0] = splittedName[0].trim();
				// First Name
				resultName[1] = splittedName[1].trim();
				// Middle Name
				resultName[2] = splittedName[2].trim();
			}
		}
		return resultName;
	}

	// Convert String Array to Integer Array
	public static Integer[] convertStringArrayToIntegerArray(String[] inputArray) {
		Integer[] outputArray = new Integer[inputArray.length];
		for (int i = 0; i < inputArray.length; i++) {
			outputArray[i] = Integer.parseInt(inputArray[i]);
		}

		return outputArray;
	}
	
	// Substring Lat and Long
	public static String subStringLatandLang(BigDecimal inputValue){
		if(inputValue!=null){
			String subStringValue=inputValue.toString();
			if(subStringValue.length()>=8){
				return subStringValue.substring(0, subStringValue.indexOf(".")+6);
			}else{
				return subStringValue;
			}
		}else{
			return "";
		}
	}

	// Split Address
	public static String[] splitAddress(String address){
		// Index Position of Address 0 - Street Address, 1- City, 2 - State, 3 - ZipCode
		String resultAddress[]=new String[10];
		if(address!=null){
			String[] splittedAddress=address.split(",");
			if(splittedAddress.length==1){
				resultAddress[0]=splittedAddress[0].trim();
			}
			else if(splittedAddress.length==2){
				
				if(isZipcode(splittedAddress[1].trim())){
					resultAddress[0]=splittedAddress[0].trim();
					resultAddress[3]=splittedAddress[1].trim();
				}else if(isState(splittedAddress[1].trim())){
					resultAddress[0]=splittedAddress[0].trim();
					resultAddress[2]=splittedAddress[1].trim();
				}else{
					resultAddress[0]=splittedAddress[0].trim();
					resultAddress[1]=splittedAddress[1].trim();
				}			

			}else if(splittedAddress.length==3){
				if(isZipcode(splittedAddress[2].trim())){
					resultAddress[0]=splittedAddress[0].trim();
					resultAddress[1]=splittedAddress[1].trim();
					resultAddress[3]=splittedAddress[2].trim();
				}else{
				resultAddress[0]=splittedAddress[0].trim();
				resultAddress[1]=splittedAddress[1].trim();
				resultAddress[2]=splittedAddress[2].trim();
				}
			}else if(splittedAddress.length==4){
				resultAddress[0]=splittedAddress[0].trim();
				resultAddress[1]=splittedAddress[1].trim();
				resultAddress[2]=splittedAddress[2].trim();
				resultAddress[3]=splittedAddress[3].trim();
			}else if(splittedAddress.length>4){
				
				resultAddress[3]=splittedAddress[splittedAddress.length-1].trim();
				resultAddress[2]=splittedAddress[splittedAddress.length-2].trim();
				resultAddress[1]=splittedAddress[splittedAddress.length-3].trim();
				resultAddress[0]="";
				for(int i=0;i<=splittedAddress.length-4;i++){
					if(i!=0){
						resultAddress[0]+=",";
					}
					resultAddress[0]+=splittedAddress[i].trim();
				}
				
			}
			
		}
		
		return resultAddress;
	}
	
	//Check for Zipcode
	public static boolean isZipcode(String checkStr){
		if(checkStr.matches("\\d+")&&checkStr.length()==5){
			return true;
		}else{
			return false;
		}		
	}
	
	//Check for State
	public static boolean isState(String checkStr){
		if(checkStr.matches("[a-zA-Z]+")&&checkStr.length()==2){
			return true;
		}else{
			return false;
		}
	}
	
	// Get IP Address From Request
	public static String getRemoteAddr(HttpServletRequest request) {
		/*final String[] IP_HEADER_CANDIDATES = { 
		    "X-Forwarded-For",
		    "Proxy-Client-IP",
		    "WL-Proxy-Client-IP",
		    "HTTP_X_FORWARDED_FOR",
		    "HTTP_X_FORWARDED",
		    "HTTP_X_CLUSTER_CLIENT_IP",
		    "HTTP_CLIENT_IP",
		    "HTTP_FORWARDED_FOR",
		    "HTTP_FORWARDED",
		    "HTTP_VIA",
		    "REMOTE_ADDR" };"unknown".equalsIgnoreCase(ip)*/
	    String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
	    if (ipFromHeader != null && ipFromHeader.length() > 0) {
	        System.out.println("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
	        return ipFromHeader;
	    }
	    return request.getRemoteAddr();
	}
}
