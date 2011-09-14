CREATE TABLE GENERICSETTINGS(
  ID INTEGER NOT NULL,
  TIGERVERSION  VARCHAR(4),
  USGSVERSION   VARCHAR(8),
  PRIMARY KEY(ID)
) ENGINE = InnoDB;

INSERT INTO GENERICSETTINGS(ID, TIGERVERSION, USGSVERSION) VALUES(1000, '2010', '20110801');

CREATE TABLE SHAPEFILEMETADATA(
    ID  INTEGER NOT NULL AUTO_INCREMENT,
    GEOID   VARCHAR(20),
    FILENAME  VARCHAR(255),
    VERSION  VARCHAR(4),
    STATUS VARCHAR(16),
    MTFCCCODE VARCHAR(6),
    PRIMARY KEY(ID)
) ENGINE = InnoDB;

alter table SHAPEFILEMETADATA AUTO_INCREMENT=1000;
create index idx_sm_geoid on SHAPEFILEMETADATA(GEOID);
create index idx_sm_mtfcccode on SHAPEFILEMETADATA(MTFCCCODE);

CREATE TABLE FEATURESMETADATA(
    ID INTEGER NOT NULL AUTO_INCREMENT,
    STATE VARCHAR(30),
    STATEABBR VARCHAR(2),
    USGSDATE VARCHAR(8),
    FILENAME  VARCHAR(255),
    STATEGEOID  VARCHAR(2),
    STATUS  VARCHAR(15),
    PRIMARY KEY(ID)
) ENGINE = InnoDB;

alter table FEATURESMETADATA AUTO_INCREMENT=1000;
create index idx_fm_stategeoid on FEATURESMETADATA(STATEGEOID);
create index idx_fm_status on FEATURESMETADATA(STATUS);

-- NOTE: FEATUREID IS NOT AN AUTO_INCREMENT!!!
CREATE TABLE FEATURE(
    FEATUREID   INTEGER NOT NULL,
    FEATURENAME        VARCHAR(100),
    FEATURECLASS  VARCHAR(100),
    LAT           NUMERIC(14,5),
    LNG           NUMERIC(14,5),
    FEATURESOURCE  VARCHAR(10),
    FEATUREMETADATAID INTEGER,
    PRIMARY KEY(FEATUREID),
    FOREIGN KEY(FEATUREMETADATAID) REFERENCES FEATURESMETADATA(ID)
) ENGINE = InnoDB;

alter table FEATURE AUTO_INCREMENT=1000;
create index idx_featureclass on FEATURE(FEATURECLASS);
create index idx_f_fmid on FEATURE(FEATUREMETADATAID);

CREATE TABLE MTFCC(
        MTFCCID       INTEGER AUTO_INCREMENT,
        MTFCCCODE     CHAR(5),
        FEATURECLASS  VARCHAR(100),
        SUPERCLASS    VARCHAR(75),
        POINT         BOOL,
        LINEAR1        BOOL,
        AREAL         BOOL,
        FEATURECLASSDESCRIPTION TEXT,
        PRIMARY KEY(MTFCCID)
) ENGINE=InnoDB;

alter table MTFCC AUTO_INCREMENT=1000;
create index idx_mtfcc_code ON MTFCC(MTFCCCODE);

CREATE TABLE LOCATION(
        LOCATIONID INTEGER NOT NULL AUTO_INCREMENT,
        GEOID VARCHAR(20),
        STATEFP VARCHAR(2),
        COUNTYFP VARCHAR(3),
        TRACTFP VARCHAR(6),
        CD111FP VARCHAR(2),
        consolidatedCityFP VARCHAR(5),
        countySubdivisionFP VARCHAR(5),
        elementaryDistrictFP VARCHAR(7),
        secondaryDistrictFP VARCHAR(5),
        unifiedDistrictFP VARCHAR(5),
        subMinorFP VARCHAR(5),
        placeFP VARCHAR(5),
        stateUpperDistrictFP VARCHAR(3),
        stateLowerDistrictFP VARCHAR(3),
        SUBBARIOFP VARCHAR(5),
        votingDistrictFP VARCHAR(6),
        classFP VARCHAR(2),
        LOCATIONNAME VARCHAR(100),
        LSAD VARCHAR(2),
        MTFCCID INTEGER,
        SHAPEFILEMETADATAID INTEGER,
        URBANRURAL CHAR(1),
        FUNCTIONALSTATUS CHAR(1),
        LANDAREA BIGINT,
        WATERAREA BIGINT,
        INTERNALLAT NUMERIC(14,5),
        INTERNALLNG NUMERIC(14,5),
        TIGERVERSION VARCHAR(8),
        MINLAT NUMERIC(14,5),
        MINLNG NUMERIC(14,5),
        MAXLAT NUMERIC(14,5),
        MAXLNG NUMERIC(14,5),
        PRIMARY KEY(LOCATIONID),
        FOREIGN KEY(MTFCCID) REFERENCES MTFCC(MTFCCID)
          ON DELETE CASCADE
          ON UPDATE CASCADE,
        FOREIGN KEY(SHAPEFILEMETADATAID) REFERENCES SHAPEFILEMETADATA(ID)
          ON UPDATE CASCADE
          ON DELETE SET NULL
) ENGINE=InnoDB;

alter table LOCATION AUTO_INCREMENT=1000;
create index idx_l_smid on LOCATION(SHAPEFILEMETADATAID);
CREATE INDEX idx_l_mtfccid on LOCATION(MTFCCID);

CREATE TABLE BORDERPOINT(
        BORDERID    BIGINT AUTO_INCREMENT,
        LOCATIONID  INTEGER,
        LAT         NUMERIC(14,5),
        LNG         NUMERIC(14,5),
        PRIMARY KEY(BORDERID),
        FOREIGN KEY(LOCATIONID) REFERENCES LOCATION(LOCATIONID)
)  ENGINE=InnoDB;

alter table BORDERPOINT AUTO_INCREMENT=1000;
create index idx_bp_l on BORDERPOINT(LOCATIONID);

ALTER TABLE LOCATION ADD CONSTRAINT FOREIGN KEY(SHAPEFILEMETADATAID) REFERENCES SHAPEFILEMETADATA(ID) ON UPDATE CASCADE;

INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3022','Mountain Peak or Summit', 'Miscellaneous Topographic Features', 'A prominent elevation rising above the surrounding level of the Earths surface.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3023','Island', 'Miscellaneous Topographic Features', 'An area of dry or relatively dry land surrounded by water or low wetland. [including archipelago, atoll, cay, hammock, hummock, isla, isle, key, moku and rock]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3024','Levee', 'Miscellaneous Topographic Features', 'An embankment flanking a stream or other flowing water feature to prevent overflow.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3026','Quarry (not water-filled), Open Pit Mine or Mine', 'Miscellaneous Topographic Features', 'An area from which commercial minerals are or were removed from the Earth; not including an oilfield or gas field.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3027','Dam', 'Miscellaneous Topographic Features', 'A barrier built across the course of a stream to impound water and/or control water flow.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3061','Cul-de-sac', 'Miscellaneous Topographic Features', 'An expanded paved area at the end of a street used by vehicles for turning around. For mapping purposes, the U.S. Census Bureau maps it only as a point feature.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3062','Traffic Circle', 'Miscellaneous Topographic Features', 'A circular intersection allowing for continuous movement of traffic at the meeting of roadways.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3066','Gate', 'Miscellaneous Topographic Features', 'A movable barrier across a road.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3067','Toll Booth', 'Miscellaneous Topographic Features', 'A structure or barrier where a fee is collected for using a road.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3070','Tower/Beacon', 'Miscellaneous Topographic Features', 'A manmade structure, higher than its diameter, generally used for observation, storage, or electronic transmission.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3071','Lookout Tower', 'Tower/Beacon', 'A manmade structure, higher than its diameter, used for observation.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3072','Transmission Tower including cell, radio and TV', 'Tower/Beacon', 'A manmade structure, higher than its diameter, used for electronic transmission.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3073','Water Tower', 'Tower/Beacon', 'A manmade structure, higher than its diameter, used for water storage.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3074','Lighthouse Beacon', 'Tower/Beacon', 'A manmade structure, higher than its diameter, used for transmission of light and possibly sound generally to aid in navigation.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3075','Tank/Tank Farm', 'Miscellaneous Topographic Features', 'One or more manmade structures, each higher than its diameter, used for liquid (other than water) or gas storage or for distribution activities.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3076','Windmill Farm', 'Miscellaneous Topographic Features', 'One or more manmade structures used to generate power from the wind.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3077','Solar Farm', 'Miscellaneous Topographic Features', 'One or more manmade structures used to generate power from the sun.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3078','Monument or Memorial', 'Miscellaneous Topographic Features', 'A manmade structure to educate, commemorate, or memorialize an event, person, or feature.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3079','Boundary Monument Point', 'Miscellaneous Topographic Features', 'A material object placed on or near a boundary line to preserve and identify the location of the boundary line on the ground.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3080','Survey Control Point', 'Miscellaneous Topographic Features', 'A point on the ground whose position (horizontal or vertical) is known and can be used as a base for additional survey work.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3081','Locality Point', 'Miscellaneous Topographic Features', 'A point that identifies the location and name of an unbounded locality (e.g., crossroad, community, populated place or locale).');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3085','Alaska Native Village Official Point', 'Miscellaneous Topographic Features', 'A point that serves as the core of an Alaska Native village and is used in defining Alaska Native village statistical areas.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('C3088','Landfill', 'Miscellaneous Topographic Features', 'A disposal facility at which solid waste is placed on or in the land.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2100','American Indian Area', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'A legally defined state- or federally recognized reservation and/off-reservation trust land (excludes statistical American Indian areas).');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2101','American Indian Area (Reservation Only)', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'American Indian Area (Reservation Only)');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2102','American Indian Area (Off-Reservation Trust Land Only)', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'American Indian Area (Off-Reservation Trust Land Only)');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2120','Hawaiian Home Land', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'A legal area held in trust for the benefit of Native Hawaiians.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2130','Alaska Native Village Statistical Area', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'A statistical geographic entity that represents the residences, permanent and/or seasonal, for Alaska Natives who are members of or receiving governmental services from the defining legal Alaska Native Village corporation.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2140','Oklahoma Tribal Statistical Area', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'A statistical entity identified and delineated by the Census Bureau in consultation with federally recognized American Indian tribes that have no current reservation, but had a former reservation in Oklahoma.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2150','State-designated Tribal Statistical Area', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'A statistical geographic entity identified and delineated for the Census Bureau by a state-appointed liaison for a state-recognized American Indian tribe that does not currently have a reservation and/or lands in trust.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2160','Tribal Designated Statistical Area', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'A statistical geographic entity identified and delineated for the Census Bureau by a federally recognized American Indian tribe that does not currently have a reservation and/or off-reservation trust land.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2170','American Indian Joint Use Area', 'American Indian, Alaska Native, Or Native Hawaiian Area', 'An area administered jointly and/or claimed by two or more American Indian tribes.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2200','Alaska Native Regional Corporation', 'Tabulation Area', 'Corporate entities established to conduct both business and nonprofit affairs of Alaska Natives pursuant to the Alaska Native Claims Settlement Act of 1972 (Public Law 92-203). There are twelve geographically defined ANRCs and they are all within and cover most of the State of Alaska (the Annette Island Reserve-an American Indian reservation-is excluded from any ANRC). The boundaries of ANRCs have been legally established.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2300','Tribal Subdivision', 'Tabulation Area', 'Administrative subdivisions of federally recognized American Indian reservations, off-reservation trust lands, or Oklahoma tribal statistical areas (OTSAs). These entities are internal units of self-government or administration that serve social, cultural, and/or economic purposes for the American Indians on the reservations, off-reservation trust lands, or OTSAs.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2400','Tribal Census Tract', 'Tabulation Area', 'A relatively small and permanent statistical subdivision of a federally recognized American Indian reservation and/or off-reservation trust land, delineated by American Indian tribal participants or the Census Bureau for the purpose of presenting demographic data.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G2410','Tribal Block Group', 'Tabulation Area', 'A cluster of census blocks within a single tribal census tract delineated by American Indian tribal participants or the Census Bureau for the purpose of presenting demographic data.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G3100','Combined Statistical Area', 'Tabulation Area', 'A grouping of adjacent metropolitan and/or micropolitan statistical areas that have a degree of economic and social integration, as measured by commuting.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G4000','State/Equivalent', 'Tabulation Area', 'The primary governmental divisions of the United States. The District of Columbia is treated as a statistical equivalent of a state for census purposes, as are Puerto Rico, American Samoa, Guam, the Commonwealth of the Northern Mariana Islands, and the U.S. Virgin Islands.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G4020','County/Equivalent', 'Tabulation Area', 'The primary division of a state or state equivalent area. The primary divisions of 48 states are termed County, but other terms are used such as Borough in Alaska, Parish in Louisiana, and Municipio in Puerto Rico. This feature includes independent cities, which are incorporated places that are not part of any county.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G3110','Metropolitan and Micropolitan Statistical Area', 'Tabulation Area', 'An area containing a substantial population nucleus together with adjacent communities having a high degree of economic and social integration with that core, as measured by commuting. Defined using whole counties and equivalents.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G3120','Metropolitan Division', 'Tabulation Area', 'A county or grouping of counties that is a subdivision of a Metropolitan Statistical Area containing an urbanized area with a population of 2.5 million or more.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5200','Congressional District', 'Tabulation Area', 'The 435 areas from which people are elected to the U.S. House of Representatives. Additional equivalent features exist for state equivalents with nonvoting delegates or no representative. The subtypes of this feature are 106th, 107th, 108th, 109th, and 110th Congressional Districts, plus subsequent Congresses.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G4120','Consolidated City', 'Tabulation Area', 'An incorporated place that has merged governmentally with a county or minor civil division, but one or more of the incorporated places continues to function within the consolidation. It is a place that contains additional separately incorporated places.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G4110','Incorporated Place', 'Tabulation Area', 'A legal entity incorporated under state law to provide general-purpose governmental services to a concentration of population. Incorporated places are generally designated as a city, borough, municipality, town, village, or, in a few instances, have no legal description.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G3200','Combined New England City and Town Area', 'Tabulation Area', 'A grouping of adjacent New England city and town areas that have a degree of economic and social integration, as measured by commuting.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G3210','New England City and Town Metropolitan and Micropolitan Statistical Area', 'Tabulation Area', 'An area containing a substantial population nucleus together with adjacent communities having a high degree of economic and social integration with that core, as measured by commuting. Defined using Minor Civil Divisions (MCDs) in New England.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G3220','New England City and Town Division', 'Tabulation Area', 'A grouping of cities and towns in New England that is a subdivision of a New England City and Town Area containing an urbanized area with a population of 2.5 million or more.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G3500','Urban Area', 'Tabulation Area', 'Densely settled territory that contains at least 2,500 people. The subtypes of this feature are Urbanized Area (UA), which consists of 50,000 + people and Urban Cluster, which ranges between 2,500 and 49,999 people.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G4040','County Subdivision', 'Tabulation Area', 'The primary divisions of counties and equivalent features for the reporting of Census Bureau data. The subtypes of this feature are Minor Civil Division, Census County Division/Census Subarea, and Unorganized Territory. This feature includes independent places, which are incorporated places that are not part of any county subdivision.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G4060','Sub-Minor Civil Division', 'Tabulation Area', 'Legally defined divisions (subbarrios) of minor civil divisions (barrios-pueblo and barrios) in Puerto Rico.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G4210','Census Designated Place', 'Tabulation Area', 'A statistical area defined for a named concentration of population and the statistical counterpart of an incorporated place.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G4300','Economic Census Place', 'Tabulation Area', 'The lowest level of geographic area for presentation of some types of Economic Census data. It includes incorporated places, consolidated cities, census designated places (CDPs), minor civil divisions (MCDs) in selected states, and balances of MCDs or counties. An incorporated place, CDP, MCD, or balance of MCD qualifies as an economic census place if it contains 5,000 or more residents, or 5,000 or more jobs, according to the most current data available.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5020','Census Tract', 'Tabulation Area', 'Relatively permanent statistical subdivisions of a County or equivalent feature delineated by local participants as part of the Census Bureaus Participant Statistical Areas Program.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5030','Block Group', 'Tabulation Area', 'A cluster of census blocks having the same first digit of their four-digit identifying numbers within a Census Tract. For example, block group 3 (BG 3) within a Census Tract includes all blocks numbered from 3000 to 3999.');
--INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5040','Tabulation Block', 'Tabulation Area', 'The lowest-order census defined statistical area. It is an area, such as a city block, bounded primarily by physical features but sometimes by invisible city or property boundaries. A tabulation block boundary does not cross the boundary of any other geographic area for which the Census Bureau tabulates data. The subtypes of this feature are Count Question Resolution (CQR), current, and census.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5210','State Legislative District (Upper Chamber)', 'Tabulation Area', 'Areas established by a state or equivalent government from which members are elected to the upper or unicameral chamber of a state governing body. The upper chamber is the senate in a bicameral legislature, and the unicameral case is a single house legislature (Nebraska).');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5220','State Legislative District (Lower Chamber)', 'Tabulation Area', 'Areas established by a state or equivalent government from which members are elected to the lower chamber of a state governing body. The lower chamber is the House of Representatives in a bicameral legislature.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5240','Voting District', 'Tabulation Area', 'The generic name for the geographic features, such as precincts, wards, and election districts, established by state, local, and tribal governments for the purpose of conducting elections.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5400','Elementary School District', 'Tabulation Area', 'A geographic area within which officials provide public elementary grade-level educational services for residents.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5410','Secondary School District', 'Tabulation Area', 'A geographic area within which officials provide public secondary grade-level educational services for residents.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G5420','Unified School District', 'Tabulation Area', 'A geographic area within which officials provide public educational services for all grade levels for residents.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G6100','Public-Use Microdata Area (1% Area)', 'Tabulation Area', 'A decennial census area with a population of at least 400,000 for which the Census Bureau provides selected extracts of household-level data from a 1% sample of long-form Census Bureau records that are screened to protect confidentiality.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G6120','Public-Use Microdata Area (5% or 10% Area)', 'Tabulation Area', 'A decennial census area with a population of at least 100,000 for which the Census Bureau provides selected extracts of household-level data from a 5% sample of long-form Census Bureau records that are screened to protect confidentiality. In Guam and the U.S. Virgin Islands, the extracts are from a 10% sample.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G6320','Traffic Analysis Zone', 'Tabulation Area', 'An area delineated by state and/or local transportation officials and Metropolitan Planning Organizations (MPOs) for tabulating journey-to-work and place-of-work data.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G6330','Urban Growth Area', 'Tabulation Area', 'An area defined under state authority to manage urbanization that the U.S. Census Bureau includes in the MAF/TIGER Database in agreement with the state.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G6340','ZIP Code Tabulation Area (Three-Digit)', 'Tabulation Area', 'An approximate statistical-area representation of a U.S. Postal Service (USPS) 3-digit ZIP Code service area.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G6350','Zip Code Tabulation Area (Five-Digit)', 'Tabulation Area', 'An approximate statistical-area representation of a U.S. Postal Service (USPS) 5-digit ZIP Code service area.');
-- INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('G6400','Commercial Region', 'Tabulation Area', 'For the purpose of presenting economic statistical data, municipios in Puerto Rico are grouped into commercial regions.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H1100','Connector', 'Hydrographic Features', 'A known, but nonspecific, hydrographic connection between two nonadjacent water features.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H2025','Swamp/Marsh', 'Hydrographic Features', 'A poorly drained wetland, fresh or saltwater, wooded or grassy, possibly covered with open water. [includes bog, cienega, marais and pocosin]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H2030','Lake/Pond', 'Hydrographic Features', 'A standing body of water that is surrounded by land.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H2040','Reservoir', 'Hydrographic Features', 'An artificially impounded body of water.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H2041','Treatment Pond', 'Hydrographic Features', 'An artificial body of water built to treat fouled water.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H2051','Bay/Estuary/Gulf/ Sound', 'Hydrographic Features', 'A body of water partly surrounded by land. [includes arm, bight, cove and inlet]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H2053','Ocean/Sea', 'Hydrographic Features', 'The great body of salt water that covers much of the earth.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H2060','Gravel Pit/Quarry filled with water', 'Hydrographic Features', 'A body of water in a place or area from which commercial minerals were removed from the Earth.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H2081','Glacier', 'Hydrographic Features', 'A body of ice moving outward and down slope from an area of accumulation; an area of relatively permanent snow or ice on the top or side of a mountain or mountainous area. [includes ice field and ice patch]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H3010','Stream/River', 'Hydrographic Features', 'A natural flowing waterway. [includes anabranch, awawa, branch, brook, creek, distributary, fork, kill, pup, rio, and run]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H3013','Braided Stream', 'Hydrographic Features', 'A natural flowing waterway with an intricate network of interlacing channels.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('H3020','Canal, Ditch or Aqueduct', 'Hydrographic Features', 'An artificial waterway constructed to transport water, to irrigate or drain land, to connect two or more bodies of water, or to serve as a waterway for watercraft. [includes lateral]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1121','Apartment Building or Complex', 'Potential Living Quarters', 'A building or group of buildings that contain multiple living quarters generally for which rent is paid.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1223','Trailer Court or Mobile Home Park', 'Potential Living Quarters', 'An area in which parking space for house trailers is rented, usually providing utilities and services.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1225','Crew-of-Vessel Location', 'Potential Living Quarters', 'A point or area in which the population of military or merchant marine vessels at sea are assigned, usually being at or near the home port pier.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1226','Housing Facility/Dormitory for Workers', 'Potential Living Quarters', 'A structure providing housing for a number of persons employed as semi-permanent or seasonal laborers.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1227','Hotel, Motel, Resort, Spa, Hostel, YMCA, or YWCA', 'Potential Living Quarters', 'A structure providing transient lodging or living quarters, generally for some payment.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1228','Campground', 'Potential Living Quarters', 'An area used for setting up mobile temporary living quarters (camp) or holding a camp meeting, sometimes providing utilities and other amenities.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1229','Shelter or Mission', 'Potential Living Quarters', 'A structure providing low-cost or free living quarters established by a welfare or educational organization for the needy people of a district.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1231','Hospital/Hospice/ Urgent Care Facility', 'Potential Living Quarters', 'One or more structures where the sick or injured may receive medical or surgical attention. [including infirmary]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1233','Nursing Home, Retirement Home, or Home for the Aged', 'Potential Living Quarters', 'A structure to house and provide care for the elderly.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1234','County Home or Poor Farm', 'Potential Living Quarters', 'One or more structures administered by a local government that serve as living quarters for the indigent.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1235','Juvenile Institution', 'Potential Living Quarters', 'A facility (correctional or non-correctional) where groups of juveniles reside; this includes training schools, detention centers, residential treatment centers and orphanages.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1236','Local Jail or Detention Center', 'Potential Living Quarters', 'One or more structures that serve as a place for the confinement of adult persons in lawful detention, administered by a local (county, municipal, etc.) government.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1237','Federal Penitentiary, State Prison, or Prison Farm', 'Potential Living Quarters', 'An institution that serves as a place for the confinement of adult persons in lawful detention, administered by the federal government or a state government.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1238','Other Correctional Institution', 'Potential Living Quarters', 'One or more structures that serve as a place for the confinement of adult persons in lawful detention, not elsewhere classified or administered by a government of unknown jurisdiction.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1239','Convent, Monastery, Rectory, Other Religious Group Quarters', 'Potential Living Quarters', 'One or more structures intended for use as a residence for those having a religious vocation.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K1241','Sorority, Fraternity, or College Dormitory', 'Potential Living Quarters', 'One or more structures associated with a social or educational organization that serve as living quarters for college students.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2100','Governmental', 'Workplaces', 'A place where employees are employed in federal, state, local, or tribal government.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2110','Military Installation', 'Governmental', 'An area owned and/or occupied by the Department of Defense for use by a branch of the armed forces (such as the Army, Navy, Air Force, Marines, or Coast Guard), or a state owned area for the use of the National Guard.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2146','Community Center', 'Governmental', 'A meeting place used by members of a community for social, cultural, or recreational purposes.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2165','Government Center', 'Governmental', 'A place used by members of government (either federal, state, local, or tribal) for administration and public business.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2167','Convention Center', 'Governmental', 'An exhibition hall or conference center with enough open space to host public and private business and social events.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2180','Park', 'Governmental', 'Parkland defined and administered by federal, state, and local governments.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2181','National Park Service Land', 'Park', 'Area�National parks, National Monuments, and so forth�under the jurisdiction of the National Park Service.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2182','National Forest or Other Federal Land', 'Park', 'Land under the management and jurisdiction of the federal government, specifically including areas designated as National Forest, and excluding areas under the jurisdiction of the National Park Service.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2183','Tribal Park, Forest, or Recreation Area', 'Park', 'A place or area set aside for recreation or preservation of a cultural or natural resource and under the administration of an American Indian tribe.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2184','State Park, Forest, or Recreation Area', 'Park', 'A place or area set aside for recreation or preservation of a cultural or natural resource and under the administration of a state government.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2185','Regional Park, Forest, or Recreation Area', 'Park', 'A place or area set aside for recreation or preservation of a cultural or natural resource and under the administration of a regional government.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2186','County Park, Forest, or Recreation Area', 'Park', 'A place or area set aside for recreation or preservation of a cultural or natural resource and under the administration of a county government.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2187','County Subdivision Park, Forest, or Recreation Area', 'Park', 'A place or area set aside for recreation or preservation of a cultural or natural resource and under the administration of a minor civil division (town/township) government.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2188','Incorporated Place Park, Forest, or Recreation Area', 'Park', 'A place or area set aside for recreation or preservation of a cultural or natural resource and under the administration of a municipal government.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2189','Private Park, Forest, or Recreation Area', 'Park', 'A privately owned place or area set aside for recreation or preservation of a cultural or natural resource.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2190','Other Park, Forest, or Recreation Area (quasi-public, independent park, commission, etc.)', 'Park', 'A place or area set aside for recreation or preservation of a cultural or natural resource and under the administration of some other type of government or agency such as an independent park authority or commission.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2191','Post Office', 'Governmental', 'An official facility of the U.S. Postal Service used for processing and distributing mail and other postal material.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2193','Fire Department', 'Governmental', 'Fire Department.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2194','Police Station', 'Governmental', 'Police Station.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2195','Library', 'Governmental', 'Library.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2196','City/Town Hall', 'Governmental', 'City/Town Hall.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2300','Commercial Workplace', 'Workplaces', 'A place of employment for wholesale, retail, or other trade.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2361','Shopping Center or Major Retail Center', 'Commercial Workplace', 'A group of retail establishments within a planned subdivision sharing a common parking area.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2362','Industrial Building or Industrial Park', 'Commercial Workplace', 'One or more manufacturing establishments within an area zoned for fabrication, construction, or other similar trades.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2363','Office Building or Office Park', 'Commercial Workplace', 'One or more structures housing employees performing business, clerical, or professional services.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2364','Farm/Vineyard/ Winery/Orchard', 'Commercial Workplace', 'An agricultural establishment where crops are grown and/or animals are raised, usually for food.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2366','Other Employment Center', 'Commercial Workplace', 'A place of employment not elsewhere classified or of unknown type.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2400','Transportation Terminal', 'Workplaces', 'A facility where one or more modes of transportation can be accessed by people or for the shipment of goods; examples of such a facility include marine terminal, bus station, train station, airport and truck warehouse.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2424','Marina', 'Transportation Terminal', 'A place where privately owned, light-craft are moored.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2432','Pier/Dock', 'Transportation Terminal', 'A platform built out from the shore into the water and supported by piles. This platform may provide access to ships and boats, or it may be used for recreational purposes.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2451','Airport or Airfield', 'Transportation Terminal', 'A manmade facility maintained for the use of aircraft. [including airstrip, landing field and landing strip]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2452','Train Station, Trolley or Mass Transit Rail Station', 'Transportation Terminal', 'A place where travelers can board and exit rail transit lines, including associated ticketing, freight, and other commercial offices.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2453','Bus Terminal', 'Transportation Terminal', 'A place where travelers can board and exit mass motor vehicle transit, including associated ticketing, freight, and other commercial offices.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2454','Marine Terminal', 'Transportation Terminal', 'A place where travelers can board and exit water transit or where cargo is handled, including associated ticketing, freight, and other commercial offices.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2455','Seaplane Anchorage', 'Transportation Terminal', 'A place where an airplane equipped with floats for landing on or taking off from a body of water can debark and load.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2456','Airport�Intermodal Transportation Hub/Terminal', 'Transportation Terminal', 'A major air transportation facility where travelers can board and exit airplanes and connect with other (i.e. non-air) modes of transportation.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2457','Airport�Statistical Representation', 'Transportation Terminal', 'The area of an airport adjusted to include whole 2000 census blocks used for the delineation of urban areas.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2458','Park and Ride Facility/Parking Lot', 'Transportation Terminal', 'A place where motorists can park their cars and transfer to other modes of transportation.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2459','Runway/Taxiway', 'Transportation Terminal', 'A fairly level and usually paved expanse used by airplanes for taking off and landing at an airport.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2460','Helicopter Landing Pad', 'Transportation Terminal', 'A fairly level and usually paved expanse used by helicopters for taking off and landing.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2540','University or College', 'Other Workplace', 'A building or group of buildings used as an institution for post-secondary study, teaching, and learning. [including seminary]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2543','School or Academy', 'Other Workplace', 'A building or group of buildings used as an institution for preschool, elementary or secondary study, teaching, and learning. [including elementary school and high school]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2545','Museum, Visitor Center, Cultural Center, or Tourist Attraction', 'Other Workplace', 'An attraction of historical, cultural, educational or other interest that provides information or displays artifacts.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2561','Golf Course', 'Other Workplace', 'A place designed for playing golf.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2564','Amusement Center', 'Other Workplace', 'A facility that offers entertainment, performances or sporting events. Examples include arena, auditorium, theater, stadium, coliseum, race course, theme park, fairgrounds and shooting range.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2582','Cemetery', 'Other Workplace', 'A place or area for burying the dead. [including burying ground and memorial garden]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K2586','Zoo', 'Other Workplace', 'A facility in which terrestrial and/or marine animals are confined within enclosures and displayed to the public for educational, preservation, and research purposes.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('K3544','Place of Worship', 'Other Workplace', 'A sanctified place or structure where people gather for religious worship; examples include church, synagogue, temple, and mosque.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4010','Pipeline', 'Miscellaneous Linear Features', 'A long tubular conduit or series of pipes, often underground, with pumps and valves for flow control, used to transport fluid (e.g., crude oil, natural gas), especially over great distances.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4020','Powerline', 'Miscellaneous Linear Features', 'One or more wires, often on elevated towers, used for conducting high-voltage electric power.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4031','Aerial Tramway/Ski Lift', 'Miscellaneous Linear Features', 'A conveyance that transports passengers or freight in carriers suspended from cables and supported by a series of towers.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4040','Conveyor', 'Miscellaneous Linear Features', 'A mechanical apparatus that uses a moving belt to transport items from one place to another.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4110','Fence Line', 'Miscellaneous Linear Features', 'A man-made barrier enclosing or bordering a field, yard, etc., usually made of posts and wire or wood, used to prevent entrance, to confine, or to mark a boundary.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4121','Ridge Line', 'Miscellaneous Linear Features', 'The line of highest elevation along a ridge.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4125','Cliff/Escarpment', 'Miscellaneous Linear Features', 'A very steep or vertical slope. [including bluff, crag, head, headland, nose, palisades, precipice, promontory, rim and rimrock]');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4130','Point-to-Point Line', 'Miscellaneous Linear Features', 'A line defined as beginning at one location point and ending at another, both of which are in sight.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4140','Property/Parcel Line (Including PLSS)', 'Miscellaneous Linear Features', 'This feature class may denote a nonvisible boundary of either public or private lands (e.g., a park boundary) or it may denote a Public Land Survey System or equivalent survey line.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('L4165','Ferry Crossing', 'Miscellaneous Linear Features', 'The route used to carry or convey people or cargo back and forth over a waterbody in a boat.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('R1011','Railroad Feature (Main, Spur, or Yard)', 'Rail Features', 'A line of fixed rails or tracks that carries mainstream railroad traffic. Such a rail line can be a main line or spur line, or part of a rail yard.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('R1051','Carline, Streetcar Track, Monorail, Other Mass Transit Rail', 'Rail Features', 'Mass transit rail lines (including lines for rapid transit, monorails, streetcars, light rail, etc.) that are typically inaccessible to mainstream railroad traffic and whose tracks are not part of a road right-of-way.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('R1052','Cog Rail Line, Incline Rail Line, Tram', 'Rail Features', 'A special purpose rail line for climbing steep grades that is typically inaccessible to mainstream railroad traffic. Note that aerial tramways and streetcars (which may also be called �trams�) are accounted for by other MTFCCs and do not belong in R1052.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1100','Primary Road', 'Road/Path Features', 'Primary roads are generally divided, limited-access highways within the interstate highway system or under state management, and are distinguished by the presence of interchanges. These highways are accessible by ramps and may include some toll highways.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1200','Secondary Road', 'Road/Path Features', 'Secondary roads are main arteries, usually in the U.S. Highway, State Highway or County Highway system. These roads have one or more lanes of traffic in each direction, may or may not be divided, and usually have at-grade intersections with many other roads and driveways. They often have both a local name and a route number.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1400','Local Neighborhood Road, Rural Road, City Street', 'Road/Path Features', 'Generally a paved non-arterial street, road, or byway that usually has a single lane of traffic in each direction. Roads in this feature class may be privately or publicly maintained. Scenic park roads would be included in this feature class, as would (depending on the region of the country) some unpaved roads.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1500','Vehicular Trail (4WD)', 'Road/Path Features', 'An unpaved dirt trail where a four-wheel drive vehicle is required. These vehicular trails are found almost exclusively in very rural areas. Minor, unpaved roads usable by ordinary cars and trucks belong in the S1400 category.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1630','Ramp', 'Road/Path Features', 'A road that allows controlled access from adjacent roads onto a limited access highway, often in the form of a cloverleaf interchange. These roads are unaddressable.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1640','Service Drive usually along a limited access highway', 'Road/Path Features', 'A road, usually paralleling a limited access highway, that provides access to structures along the highway. These roads can be named and may intersect with other roads.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1710','Walkway/Pedestrian Trail', 'Road/Path Features', 'A path that is used for walking, being either too narrow for or legally restricted from vehicular traffic.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1720','Stairway', 'Road/Path Features', 'A pedestrian passageway from one level to another by a series of steps.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1730','Alley', 'Road/Path Features', 'A service road that does not generally have associated addressed structures and is usually unnamed. It is located at the rear of buildings and properties and is used for deliveries.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1740','Private Road for service vehicles (logging, oil fields, ranches, etc.)', 'Road/Path Features', 'A road within private property that is privately maintained for service, extractive, or other purposes. These roads are often unnamed.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1750','Internal U.S. Census Bureau use', 'Road/Path Features', 'Internal U.S. Census Bureau use.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1780','Parking Lot Road', 'Road/Path Features', 'The main travel route for vehicles through a paved parking area.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1820','Bike Path or Trail', 'Road/Path Features', 'A path that is used for manual or small, motorized bicycles, being either too narrow for or legally restricted from vehicular traffic.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S1830','Bridle Path', 'Road/Path Features', 'A path that is used for horses, being either too narrow for or legally restricted from vehicular traffic.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('S2000','Road Median', 'Road/Path Features', 'The unpaved area or barrier between the carriageways of a divided road.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('P0001','Nonvisible Linear Legal/Statistical Boundary', 'Bounding Edges', 'A legal/statistical boundary line that does not correspond to a shoreline or other visible feature on the ground.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('P0002','Perennial Shoreline', 'Bounding Edges', 'The more-or-less permanent boundary between land and water for a water feature that exists year-round.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('P0003','Intermittent Shoreline', 'Bounding Edges', 'The boundary between land and water (when water is present) for a water feature that does not exist year-round.');
INSERT INTO MTFCC(MTFCCCODE,FEATURECLASS,SUPERCLASS,FEATURECLASSDESCRIPTION) VALUES ('P0004','Other non-visible bounding Edge (e.g., Census water boundary, boundary of an areal feature)', 'Bounding Edges', 'A bounding Edge that does not represent a legal/statistical boundary, and does not correspond to a shoreline or other visible feature on the ground. Many such Edges bound area landmarks, while many others separate water features from each other (e.g., where a bay meets the ocean).');
