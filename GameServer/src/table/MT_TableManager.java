package table;
import java.util.HashMap;
public class MT_TableManager {;
	private HashMap<String,Integer> m_ModifiedTableMap = new HashMap<>();
	
	public void pushModifiedTable(String name) {
	if (m_ModifiedTableMap.containsKey(name))
		return;
		m_ModifiedTableMap.put(name, 0);
	}
    private MT_Table_Active mTableActive = null;
    public MT_Table_Active TableActive() {
        if (mTableActive == null) {
            try {
                mTableActive = new MT_Table_Active("Active");
                mTableActive.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableActive is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Active")){
        	try {
        		m_ModifiedTableMap.remove("Active");
				MT_Table_Active temp = new MT_Table_Active("Active");
				temp.Initialize();
				mTableActive = temp;
			} catch (Exception e) {
				TableUtil.Error("TableActive is error : ",e);
			}
        }
        return mTableActive;
    }
    private MT_Table_GSlotsBackRate mTableGSlotsBackRate = null;
    public MT_Table_GSlotsBackRate TableGSlotsBackRate() {
        if (mTableGSlotsBackRate == null) {
            try {
                mTableGSlotsBackRate = new MT_Table_GSlotsBackRate("GSlotsBackRate");
                mTableGSlotsBackRate.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGSlotsBackRate is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsBackRate")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsBackRate");
				MT_Table_GSlotsBackRate temp = new MT_Table_GSlotsBackRate("GSlotsBackRate");
				temp.Initialize();
				mTableGSlotsBackRate = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGSlotsBackRate is error : ",e);
			}
        }
        return mTableGSlotsBackRate;
    }
    private MT_Table_GSlotsResult mTableGSlotsResult = null;
    public MT_Table_GSlotsResult TableGSlotsResult() {
        if (mTableGSlotsResult == null) {
            try {
                mTableGSlotsResult = new MT_Table_GSlotsResult("GSlotsResult");
                mTableGSlotsResult.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGSlotsResult is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsResult")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsResult");
				MT_Table_GSlotsResult temp = new MT_Table_GSlotsResult("GSlotsResult");
				temp.Initialize();
				mTableGSlotsResult = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGSlotsResult is error : ",e);
			}
        }
        return mTableGSlotsResult;
    }
    private MT_Table_GEgg mTableGEgg = null;
    public MT_Table_GEgg TableGEgg() {
        if (mTableGEgg == null) {
            try {
                mTableGEgg = new MT_Table_GEgg("GEgg");
                mTableGEgg.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGEgg is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GEgg")){
        	try {
        		m_ModifiedTableMap.remove("GEgg");
				MT_Table_GEgg temp = new MT_Table_GEgg("GEgg");
				temp.Initialize();
				mTableGEgg = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGEgg is error : ",e);
			}
        }
        return mTableGEgg;
    }
    private MT_Table_GBJLStraight mTableGBJLStraight = null;
    public MT_Table_GBJLStraight TableGBJLStraight() {
        if (mTableGBJLStraight == null) {
            try {
                mTableGBJLStraight = new MT_Table_GBJLStraight("GBJLStraight");
                mTableGBJLStraight.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGBJLStraight is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GBJLStraight")){
        	try {
        		m_ModifiedTableMap.remove("GBJLStraight");
				MT_Table_GBJLStraight temp = new MT_Table_GBJLStraight("GBJLStraight");
				temp.Initialize();
				mTableGBJLStraight = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGBJLStraight is error : ",e);
			}
        }
        return mTableGBJLStraight;
    }
    private MT_Table_GBJLBet mTableGBJLBet = null;
    public MT_Table_GBJLBet TableGBJLBet() {
        if (mTableGBJLBet == null) {
            try {
                mTableGBJLBet = new MT_Table_GBJLBet("GBJLBet");
                mTableGBJLBet.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGBJLBet is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GBJLBet")){
        	try {
        		m_ModifiedTableMap.remove("GBJLBet");
				MT_Table_GBJLBet temp = new MT_Table_GBJLBet("GBJLBet");
				temp.Initialize();
				mTableGBJLBet = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGBJLBet is error : ",e);
			}
        }
        return mTableGBJLBet;
    }
    private MT_Table_GNiuBet mTableGNiuBet = null;
    public MT_Table_GNiuBet TableGNiuBet() {
        if (mTableGNiuBet == null) {
            try {
                mTableGNiuBet = new MT_Table_GNiuBet("GNiuBet");
                mTableGNiuBet.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGNiuBet is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GNiuBet")){
        	try {
        		m_ModifiedTableMap.remove("GNiuBet");
				MT_Table_GNiuBet temp = new MT_Table_GNiuBet("GNiuBet");
				temp.Initialize();
				mTableGNiuBet = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGNiuBet is error : ",e);
			}
        }
        return mTableGNiuBet;
    }
    private MT_Table_GNiuCardStraight mTableGNiuCardStraight = null;
    public MT_Table_GNiuCardStraight TableGNiuCardStraight() {
        if (mTableGNiuCardStraight == null) {
            try {
                mTableGNiuCardStraight = new MT_Table_GNiuCardStraight("GNiuCardStraight");
                mTableGNiuCardStraight.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGNiuCardStraight is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GNiuCardStraight")){
        	try {
        		m_ModifiedTableMap.remove("GNiuCardStraight");
				MT_Table_GNiuCardStraight temp = new MT_Table_GNiuCardStraight("GNiuCardStraight");
				temp.Initialize();
				mTableGNiuCardStraight = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGNiuCardStraight is error : ",e);
			}
        }
        return mTableGNiuCardStraight;
    }
    private MT_Table_AirSupport mTableAirSupport = null;
    public MT_Table_AirSupport TableAirSupport() {
        if (mTableAirSupport == null) {
            try {
                mTableAirSupport = new MT_Table_AirSupport("AirSupport");
                mTableAirSupport.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableAirSupport is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("AirSupport")){
        	try {
        		m_ModifiedTableMap.remove("AirSupport");
				MT_Table_AirSupport temp = new MT_Table_AirSupport("AirSupport");
				temp.Initialize();
				mTableAirSupport = temp;
			} catch (Exception e) {
				TableUtil.Error("TableAirSupport is error : ",e);
			}
        }
        return mTableAirSupport;
    }
    private MT_Table_Buff mTableBuff = null;
    public MT_Table_Buff TableBuff() {
        if (mTableBuff == null) {
            try {
                mTableBuff = new MT_Table_Buff("Buff");
                mTableBuff.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableBuff is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Buff")){
        	try {
        		m_ModifiedTableMap.remove("Buff");
				MT_Table_Buff temp = new MT_Table_Buff("Buff");
				temp.Initialize();
				mTableBuff = temp;
			} catch (Exception e) {
				TableUtil.Error("TableBuff is error : ",e);
			}
        }
        return mTableBuff;
    }
    private MT_Table_BuildBuyMoney mTableBuildBuyMoney = null;
    public MT_Table_BuildBuyMoney TableBuildBuyMoney() {
        if (mTableBuildBuyMoney == null) {
            try {
                mTableBuildBuyMoney = new MT_Table_BuildBuyMoney("BuildBuyMoney");
                mTableBuildBuyMoney.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableBuildBuyMoney is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("BuildBuyMoney")){
        	try {
        		m_ModifiedTableMap.remove("BuildBuyMoney");
				MT_Table_BuildBuyMoney temp = new MT_Table_BuildBuyMoney("BuildBuyMoney");
				temp.Initialize();
				mTableBuildBuyMoney = temp;
			} catch (Exception e) {
				TableUtil.Error("TableBuildBuyMoney is error : ",e);
			}
        }
        return mTableBuildBuyMoney;
    }
    private MT_Table_Building mTableBuilding = null;
    public MT_Table_Building TableBuilding() {
        if (mTableBuilding == null) {
            try {
                mTableBuilding = new MT_Table_Building("Building");
                mTableBuilding.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableBuilding is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Building")){
        	try {
        		m_ModifiedTableMap.remove("Building");
				MT_Table_Building temp = new MT_Table_Building("Building");
				temp.Initialize();
				mTableBuilding = temp;
			} catch (Exception e) {
				TableUtil.Error("TableBuilding is error : ",e);
			}
        }
        return mTableBuilding;
    }
    private MT_Table_BuildNumLimit mTableBuildNumLimit = null;
    public MT_Table_BuildNumLimit TableBuildNumLimit() {
        if (mTableBuildNumLimit == null) {
            try {
                mTableBuildNumLimit = new MT_Table_BuildNumLimit("BuildNumLimit");
                mTableBuildNumLimit.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableBuildNumLimit is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("BuildNumLimit")){
        	try {
        		m_ModifiedTableMap.remove("BuildNumLimit");
				MT_Table_BuildNumLimit temp = new MT_Table_BuildNumLimit("BuildNumLimit");
				temp.Initialize();
				mTableBuildNumLimit = temp;
			} catch (Exception e) {
				TableUtil.Error("TableBuildNumLimit is error : ",e);
			}
        }
        return mTableBuildNumLimit;
    }
    private MT_Table_commodity mTablecommodity = null;
    public MT_Table_commodity Tablecommodity() {
        if (mTablecommodity == null) {
            try {
                mTablecommodity = new MT_Table_commodity("commodity");
                mTablecommodity.Initialize();
            } catch (Exception e) {
                TableUtil.Error("Tablecommodity is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("commodity")){
        	try {
        		m_ModifiedTableMap.remove("commodity");
				MT_Table_commodity temp = new MT_Table_commodity("commodity");
				temp.Initialize();
				mTablecommodity = temp;
			} catch (Exception e) {
				TableUtil.Error("Tablecommodity is error : ",e);
			}
        }
        return mTablecommodity;
    }
    private MT_Table_Corps mTableCorps = null;
    public MT_Table_Corps TableCorps() {
        if (mTableCorps == null) {
            try {
                mTableCorps = new MT_Table_Corps("Corps");
                mTableCorps.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableCorps is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Corps")){
        	try {
        		m_ModifiedTableMap.remove("Corps");
				MT_Table_Corps temp = new MT_Table_Corps("Corps");
				temp.Initialize();
				mTableCorps = temp;
			} catch (Exception e) {
				TableUtil.Error("TableCorps is error : ",e);
			}
        }
        return mTableCorps;
    }
    private MT_Table_Country mTableCountry = null;
    public MT_Table_Country TableCountry() {
        if (mTableCountry == null) {
            try {
                mTableCountry = new MT_Table_Country("Country");
                mTableCountry.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableCountry is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Country")){
        	try {
        		m_ModifiedTableMap.remove("Country");
				MT_Table_Country temp = new MT_Table_Country("Country");
				temp.Initialize();
				mTableCountry = temp;
			} catch (Exception e) {
				TableUtil.Error("TableCountry is error : ",e);
			}
        }
        return mTableCountry;
    }
    private MT_Table_Decoratebuild mTableDecoratebuild = null;
    public MT_Table_Decoratebuild TableDecoratebuild() {
        if (mTableDecoratebuild == null) {
            try {
                mTableDecoratebuild = new MT_Table_Decoratebuild("Decoratebuild");
                mTableDecoratebuild.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableDecoratebuild is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Decoratebuild")){
        	try {
        		m_ModifiedTableMap.remove("Decoratebuild");
				MT_Table_Decoratebuild temp = new MT_Table_Decoratebuild("Decoratebuild");
				temp.Initialize();
				mTableDecoratebuild = temp;
			} catch (Exception e) {
				TableUtil.Error("TableDecoratebuild is error : ",e);
			}
        }
        return mTableDecoratebuild;
    }
    private MT_Table_DropOut mTableDropOut = null;
    public MT_Table_DropOut TableDropOut() {
        if (mTableDropOut == null) {
            try {
                mTableDropOut = new MT_Table_DropOut("DropOut");
                mTableDropOut.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableDropOut is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("DropOut")){
        	try {
        		m_ModifiedTableMap.remove("DropOut");
				MT_Table_DropOut temp = new MT_Table_DropOut("DropOut");
				temp.Initialize();
				mTableDropOut = temp;
			} catch (Exception e) {
				TableUtil.Error("TableDropOut is error : ",e);
			}
        }
        return mTableDropOut;
    }
    private MT_Table_EquipAttribute mTableEquipAttribute = null;
    public MT_Table_EquipAttribute TableEquipAttribute() {
        if (mTableEquipAttribute == null) {
            try {
                mTableEquipAttribute = new MT_Table_EquipAttribute("EquipAttribute");
                mTableEquipAttribute.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableEquipAttribute is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("EquipAttribute")){
        	try {
        		m_ModifiedTableMap.remove("EquipAttribute");
				MT_Table_EquipAttribute temp = new MT_Table_EquipAttribute("EquipAttribute");
				temp.Initialize();
				mTableEquipAttribute = temp;
			} catch (Exception e) {
				TableUtil.Error("TableEquipAttribute is error : ",e);
			}
        }
        return mTableEquipAttribute;
    }
    private MT_Table_Exp mTableExp = null;
    public MT_Table_Exp TableExp() {
        if (mTableExp == null) {
            try {
                mTableExp = new MT_Table_Exp("Exp");
                mTableExp.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableExp is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Exp")){
        	try {
        		m_ModifiedTableMap.remove("Exp");
				MT_Table_Exp temp = new MT_Table_Exp("Exp");
				temp.Initialize();
				mTableExp = temp;
			} catch (Exception e) {
				TableUtil.Error("TableExp is error : ",e);
			}
        }
        return mTableExp;
    }
    private MT_Table_Guide mTableGuide = null;
    public MT_Table_Guide TableGuide() {
        if (mTableGuide == null) {
            try {
                mTableGuide = new MT_Table_Guide("Guide");
                mTableGuide.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGuide is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Guide")){
        	try {
        		m_ModifiedTableMap.remove("Guide");
				MT_Table_Guide temp = new MT_Table_Guide("Guide");
				temp.Initialize();
				mTableGuide = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGuide is error : ",e);
			}
        }
        return mTableGuide;
    }
    private MT_Table_GuideLimit mTableGuideLimit = null;
    public MT_Table_GuideLimit TableGuideLimit() {
        if (mTableGuideLimit == null) {
            try {
                mTableGuideLimit = new MT_Table_GuideLimit("GuideLimit");
                mTableGuideLimit.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableGuideLimit is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GuideLimit")){
        	try {
        		m_ModifiedTableMap.remove("GuideLimit");
				MT_Table_GuideLimit temp = new MT_Table_GuideLimit("GuideLimit");
				temp.Initialize();
				mTableGuideLimit = temp;
			} catch (Exception e) {
				TableUtil.Error("TableGuideLimit is error : ",e);
			}
        }
        return mTableGuideLimit;
    }
    private MT_Table_Head mTableHead = null;
    public MT_Table_Head TableHead() {
        if (mTableHead == null) {
            try {
                mTableHead = new MT_Table_Head("Head");
                mTableHead.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableHead is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Head")){
        	try {
        		m_ModifiedTableMap.remove("Head");
				MT_Table_Head temp = new MT_Table_Head("Head");
				temp.Initialize();
				mTableHead = temp;
			} catch (Exception e) {
				TableUtil.Error("TableHead is error : ",e);
			}
        }
        return mTableHead;
    }
    private MT_Table_Hero mTableHero = null;
    public MT_Table_Hero TableHero() {
        if (mTableHero == null) {
            try {
                mTableHero = new MT_Table_Hero("Hero");
                mTableHero.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableHero is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Hero")){
        	try {
        		m_ModifiedTableMap.remove("Hero");
				MT_Table_Hero temp = new MT_Table_Hero("Hero");
				temp.Initialize();
				mTableHero = temp;
			} catch (Exception e) {
				TableUtil.Error("TableHero is error : ",e);
			}
        }
        return mTableHero;
    }
    private MT_Table_HeroCall mTableHeroCall = null;
    public MT_Table_HeroCall TableHeroCall() {
        if (mTableHeroCall == null) {
            try {
                mTableHeroCall = new MT_Table_HeroCall("HeroCall");
                mTableHeroCall.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableHeroCall is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("HeroCall")){
        	try {
        		m_ModifiedTableMap.remove("HeroCall");
				MT_Table_HeroCall temp = new MT_Table_HeroCall("HeroCall");
				temp.Initialize();
				mTableHeroCall = temp;
			} catch (Exception e) {
				TableUtil.Error("TableHeroCall is error : ",e);
			}
        }
        return mTableHeroCall;
    }
    private MT_Table_HeroInforce mTableHeroInforce = null;
    public MT_Table_HeroInforce TableHeroInforce() {
        if (mTableHeroInforce == null) {
            try {
                mTableHeroInforce = new MT_Table_HeroInforce("HeroInforce");
                mTableHeroInforce.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableHeroInforce is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("HeroInforce")){
        	try {
        		m_ModifiedTableMap.remove("HeroInforce");
				MT_Table_HeroInforce temp = new MT_Table_HeroInforce("HeroInforce");
				temp.Initialize();
				mTableHeroInforce = temp;
			} catch (Exception e) {
				TableUtil.Error("TableHeroInforce is error : ",e);
			}
        }
        return mTableHeroInforce;
    }
    private MT_Table_HeroInforceLv mTableHeroInforceLv = null;
    public MT_Table_HeroInforceLv TableHeroInforceLv() {
        if (mTableHeroInforceLv == null) {
            try {
                mTableHeroInforceLv = new MT_Table_HeroInforceLv("HeroInforceLv");
                mTableHeroInforceLv.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableHeroInforceLv is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("HeroInforceLv")){
        	try {
        		m_ModifiedTableMap.remove("HeroInforceLv");
				MT_Table_HeroInforceLv temp = new MT_Table_HeroInforceLv("HeroInforceLv");
				temp.Initialize();
				mTableHeroInforceLv = temp;
			} catch (Exception e) {
				TableUtil.Error("TableHeroInforceLv is error : ",e);
			}
        }
        return mTableHeroInforceLv;
    }
    private MT_Table_HeroPath mTableHeroPath = null;
    public MT_Table_HeroPath TableHeroPath() {
        if (mTableHeroPath == null) {
            try {
                mTableHeroPath = new MT_Table_HeroPath("HeroPath");
                mTableHeroPath.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableHeroPath is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("HeroPath")){
        	try {
        		m_ModifiedTableMap.remove("HeroPath");
				MT_Table_HeroPath temp = new MT_Table_HeroPath("HeroPath");
				temp.Initialize();
				mTableHeroPath = temp;
			} catch (Exception e) {
				TableUtil.Error("TableHeroPath is error : ",e);
			}
        }
        return mTableHeroPath;
    }
    private MT_Table_HeroPathConfig mTableHeroPathConfig = null;
    public MT_Table_HeroPathConfig TableHeroPathConfig() {
        if (mTableHeroPathConfig == null) {
            try {
                mTableHeroPathConfig = new MT_Table_HeroPathConfig("HeroPathConfig");
                mTableHeroPathConfig.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableHeroPathConfig is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("HeroPathConfig")){
        	try {
        		m_ModifiedTableMap.remove("HeroPathConfig");
				MT_Table_HeroPathConfig temp = new MT_Table_HeroPathConfig("HeroPathConfig");
				temp.Initialize();
				mTableHeroPathConfig = temp;
			} catch (Exception e) {
				TableUtil.Error("TableHeroPathConfig is error : ",e);
			}
        }
        return mTableHeroPathConfig;
    }
    private MT_Table_IEffect mTableIEffect = null;
    public MT_Table_IEffect TableIEffect() {
        if (mTableIEffect == null) {
            try {
                mTableIEffect = new MT_Table_IEffect("IEffect");
                mTableIEffect.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableIEffect is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("IEffect")){
        	try {
        		m_ModifiedTableMap.remove("IEffect");
				MT_Table_IEffect temp = new MT_Table_IEffect("IEffect");
				temp.Initialize();
				mTableIEffect = temp;
			} catch (Exception e) {
				TableUtil.Error("TableIEffect is error : ",e);
			}
        }
        return mTableIEffect;
    }
    private MT_Table_Instance mTableInstance = null;
    public MT_Table_Instance TableInstance() {
        if (mTableInstance == null) {
            try {
                mTableInstance = new MT_Table_Instance("Instance");
                mTableInstance.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableInstance is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Instance")){
        	try {
        		m_ModifiedTableMap.remove("Instance");
				MT_Table_Instance temp = new MT_Table_Instance("Instance");
				temp.Initialize();
				mTableInstance = temp;
			} catch (Exception e) {
				TableUtil.Error("TableInstance is error : ",e);
			}
        }
        return mTableInstance;
    }
    private MT_Table_InstanceReward mTableInstanceReward = null;
    public MT_Table_InstanceReward TableInstanceReward() {
        if (mTableInstanceReward == null) {
            try {
                mTableInstanceReward = new MT_Table_InstanceReward("InstanceReward");
                mTableInstanceReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableInstanceReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("InstanceReward")){
        	try {
        		m_ModifiedTableMap.remove("InstanceReward");
				MT_Table_InstanceReward temp = new MT_Table_InstanceReward("InstanceReward");
				temp.Initialize();
				mTableInstanceReward = temp;
			} catch (Exception e) {
				TableUtil.Error("TableInstanceReward is error : ",e);
			}
        }
        return mTableInstanceReward;
    }
    private MT_Table_IntensifyConfig mTableIntensifyConfig = null;
    public MT_Table_IntensifyConfig TableIntensifyConfig() {
        if (mTableIntensifyConfig == null) {
            try {
                mTableIntensifyConfig = new MT_Table_IntensifyConfig("IntensifyConfig");
                mTableIntensifyConfig.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableIntensifyConfig is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("IntensifyConfig")){
        	try {
        		m_ModifiedTableMap.remove("IntensifyConfig");
				MT_Table_IntensifyConfig temp = new MT_Table_IntensifyConfig("IntensifyConfig");
				temp.Initialize();
				mTableIntensifyConfig = temp;
			} catch (Exception e) {
				TableUtil.Error("TableIntensifyConfig is error : ",e);
			}
        }
        return mTableIntensifyConfig;
    }
    private MT_Table_ip_lib mTableip_lib = null;
    public MT_Table_ip_lib Tableip_lib() {
        if (mTableip_lib == null) {
            try {
                mTableip_lib = new MT_Table_ip_lib("ip_lib");
                mTableip_lib.Initialize();
            } catch (Exception e) {
                TableUtil.Error("Tableip_lib is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("ip_lib")){
        	try {
        		m_ModifiedTableMap.remove("ip_lib");
				MT_Table_ip_lib temp = new MT_Table_ip_lib("ip_lib");
				temp.Initialize();
				mTableip_lib = temp;
			} catch (Exception e) {
				TableUtil.Error("Tableip_lib is error : ",e);
			}
        }
        return mTableip_lib;
    }
    private MT_Table_Item mTableItem = null;
    public MT_Table_Item TableItem() {
        if (mTableItem == null) {
            try {
                mTableItem = new MT_Table_Item("Item");
                mTableItem.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableItem is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Item")){
        	try {
        		m_ModifiedTableMap.remove("Item");
				MT_Table_Item temp = new MT_Table_Item("Item");
				temp.Initialize();
				mTableItem = temp;
			} catch (Exception e) {
				TableUtil.Error("TableItem is error : ",e);
			}
        }
        return mTableItem;
    }
    private MT_Table_ItemSplit mTableItemSplit = null;
    public MT_Table_ItemSplit TableItemSplit() {
        if (mTableItemSplit == null) {
            try {
                mTableItemSplit = new MT_Table_ItemSplit("ItemSplit");
                mTableItemSplit.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableItemSplit is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("ItemSplit")){
        	try {
        		m_ModifiedTableMap.remove("ItemSplit");
				MT_Table_ItemSplit temp = new MT_Table_ItemSplit("ItemSplit");
				temp.Initialize();
				mTableItemSplit = temp;
			} catch (Exception e) {
				TableUtil.Error("TableItemSplit is error : ",e);
			}
        }
        return mTableItemSplit;
    }
    private MT_Table_ItemStrengthen mTableItemStrengthen = null;
    public MT_Table_ItemStrengthen TableItemStrengthen() {
        if (mTableItemStrengthen == null) {
            try {
                mTableItemStrengthen = new MT_Table_ItemStrengthen("ItemStrengthen");
                mTableItemStrengthen.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableItemStrengthen is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("ItemStrengthen")){
        	try {
        		m_ModifiedTableMap.remove("ItemStrengthen");
				MT_Table_ItemStrengthen temp = new MT_Table_ItemStrengthen("ItemStrengthen");
				temp.Initialize();
				mTableItemStrengthen = temp;
			} catch (Exception e) {
				TableUtil.Error("TableItemStrengthen is error : ",e);
			}
        }
        return mTableItemStrengthen;
    }
    private MT_Table_Land mTableLand = null;
    public MT_Table_Land TableLand() {
        if (mTableLand == null) {
            try {
                mTableLand = new MT_Table_Land("Land");
                mTableLand.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLand is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Land")){
        	try {
        		m_ModifiedTableMap.remove("Land");
				MT_Table_Land temp = new MT_Table_Land("Land");
				temp.Initialize();
				mTableLand = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLand is error : ",e);
			}
        }
        return mTableLand;
    }
    private MT_Table_legion mTablelegion = null;
    public MT_Table_legion Tablelegion() {
        if (mTablelegion == null) {
            try {
                mTablelegion = new MT_Table_legion("legion");
                mTablelegion.Initialize();
            } catch (Exception e) {
                TableUtil.Error("Tablelegion is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("legion")){
        	try {
        		m_ModifiedTableMap.remove("legion");
				MT_Table_legion temp = new MT_Table_legion("legion");
				temp.Initialize();
				mTablelegion = temp;
			} catch (Exception e) {
				TableUtil.Error("Tablelegion is error : ",e);
			}
        }
        return mTablelegion;
    }
    private MT_Table_legionConfig mTablelegionConfig = null;
    public MT_Table_legionConfig TablelegionConfig() {
        if (mTablelegionConfig == null) {
            try {
                mTablelegionConfig = new MT_Table_legionConfig("legionConfig");
                mTablelegionConfig.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TablelegionConfig is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("legionConfig")){
        	try {
        		m_ModifiedTableMap.remove("legionConfig");
				MT_Table_legionConfig temp = new MT_Table_legionConfig("legionConfig");
				temp.Initialize();
				mTablelegionConfig = temp;
			} catch (Exception e) {
				TableUtil.Error("TablelegionConfig is error : ",e);
			}
        }
        return mTablelegionConfig;
    }
    private MT_Table_LegionPlayerExp mTableLegionPlayerExp = null;
    public MT_Table_LegionPlayerExp TableLegionPlayerExp() {
        if (mTableLegionPlayerExp == null) {
            try {
                mTableLegionPlayerExp = new MT_Table_LegionPlayerExp("LegionPlayerExp");
                mTableLegionPlayerExp.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLegionPlayerExp is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("LegionPlayerExp")){
        	try {
        		m_ModifiedTableMap.remove("LegionPlayerExp");
				MT_Table_LegionPlayerExp temp = new MT_Table_LegionPlayerExp("LegionPlayerExp");
				temp.Initialize();
				mTableLegionPlayerExp = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLegionPlayerExp is error : ",e);
			}
        }
        return mTableLegionPlayerExp;
    }
    private MT_Table_LegionStore mTableLegionStore = null;
    public MT_Table_LegionStore TableLegionStore() {
        if (mTableLegionStore == null) {
            try {
                mTableLegionStore = new MT_Table_LegionStore("LegionStore");
                mTableLegionStore.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLegionStore is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("LegionStore")){
        	try {
        		m_ModifiedTableMap.remove("LegionStore");
				MT_Table_LegionStore temp = new MT_Table_LegionStore("LegionStore");
				temp.Initialize();
				mTableLegionStore = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLegionStore is error : ",e);
			}
        }
        return mTableLegionStore;
    }
    private MT_Table_LegionStoreGroup mTableLegionStoreGroup = null;
    public MT_Table_LegionStoreGroup TableLegionStoreGroup() {
        if (mTableLegionStoreGroup == null) {
            try {
                mTableLegionStoreGroup = new MT_Table_LegionStoreGroup("LegionStoreGroup");
                mTableLegionStoreGroup.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLegionStoreGroup is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("LegionStoreGroup")){
        	try {
        		m_ModifiedTableMap.remove("LegionStoreGroup");
				MT_Table_LegionStoreGroup temp = new MT_Table_LegionStoreGroup("LegionStoreGroup");
				temp.Initialize();
				mTableLegionStoreGroup = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLegionStoreGroup is error : ",e);
			}
        }
        return mTableLegionStoreGroup;
    }
    private MT_Table_LevelHint mTableLevelHint = null;
    public MT_Table_LevelHint TableLevelHint() {
        if (mTableLevelHint == null) {
            try {
                mTableLevelHint = new MT_Table_LevelHint("LevelHint");
                mTableLevelHint.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLevelHint is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("LevelHint")){
        	try {
        		m_ModifiedTableMap.remove("LevelHint");
				MT_Table_LevelHint temp = new MT_Table_LevelHint("LevelHint");
				temp.Initialize();
				mTableLevelHint = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLevelHint is error : ",e);
			}
        }
        return mTableLevelHint;
    }
    private MT_Table_LevelReward mTableLevelReward = null;
    public MT_Table_LevelReward TableLevelReward() {
        if (mTableLevelReward == null) {
            try {
                mTableLevelReward = new MT_Table_LevelReward("LevelReward");
                mTableLevelReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLevelReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("LevelReward")){
        	try {
        		m_ModifiedTableMap.remove("LevelReward");
				MT_Table_LevelReward temp = new MT_Table_LevelReward("LevelReward");
				temp.Initialize();
				mTableLevelReward = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLevelReward is error : ",e);
			}
        }
        return mTableLevelReward;
    }
    private MT_Table_LoginMail mTableLoginMail = null;
    public MT_Table_LoginMail TableLoginMail() {
        if (mTableLoginMail == null) {
            try {
                mTableLoginMail = new MT_Table_LoginMail("LoginMail");
                mTableLoginMail.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLoginMail is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("LoginMail")){
        	try {
        		m_ModifiedTableMap.remove("LoginMail");
				MT_Table_LoginMail temp = new MT_Table_LoginMail("LoginMail");
				temp.Initialize();
				mTableLoginMail = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLoginMail is error : ",e);
			}
        }
        return mTableLoginMail;
    }
    private MT_Table_LoginReward mTableLoginReward = null;
    public MT_Table_LoginReward TableLoginReward() {
        if (mTableLoginReward == null) {
            try {
                mTableLoginReward = new MT_Table_LoginReward("LoginReward");
                mTableLoginReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLoginReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("LoginReward")){
        	try {
        		m_ModifiedTableMap.remove("LoginReward");
				MT_Table_LoginReward temp = new MT_Table_LoginReward("LoginReward");
				temp.Initialize();
				mTableLoginReward = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLoginReward is error : ",e);
			}
        }
        return mTableLoginReward;
    }
    private MT_Table_Luckdraw mTableLuckdraw = null;
    public MT_Table_Luckdraw TableLuckdraw() {
        if (mTableLuckdraw == null) {
            try {
                mTableLuckdraw = new MT_Table_Luckdraw("Luckdraw");
                mTableLuckdraw.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLuckdraw is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Luckdraw")){
        	try {
        		m_ModifiedTableMap.remove("Luckdraw");
				MT_Table_Luckdraw temp = new MT_Table_Luckdraw("Luckdraw");
				temp.Initialize();
				mTableLuckdraw = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLuckdraw is error : ",e);
			}
        }
        return mTableLuckdraw;
    }
    private MT_Table_LuckDrawMedal mTableLuckDrawMedal = null;
    public MT_Table_LuckDrawMedal TableLuckDrawMedal() {
        if (mTableLuckDrawMedal == null) {
            try {
                mTableLuckDrawMedal = new MT_Table_LuckDrawMedal("LuckDrawMedal");
                mTableLuckDrawMedal.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableLuckDrawMedal is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("LuckDrawMedal")){
        	try {
        		m_ModifiedTableMap.remove("LuckDrawMedal");
				MT_Table_LuckDrawMedal temp = new MT_Table_LuckDrawMedal("LuckDrawMedal");
				temp.Initialize();
				mTableLuckDrawMedal = temp;
			} catch (Exception e) {
				TableUtil.Error("TableLuckDrawMedal is error : ",e);
			}
        }
        return mTableLuckDrawMedal;
    }
    private MT_Table_MedalAttribute mTableMedalAttribute = null;
    public MT_Table_MedalAttribute TableMedalAttribute() {
        if (mTableMedalAttribute == null) {
            try {
                mTableMedalAttribute = new MT_Table_MedalAttribute("MedalAttribute");
                mTableMedalAttribute.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableMedalAttribute is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("MedalAttribute")){
        	try {
        		m_ModifiedTableMap.remove("MedalAttribute");
				MT_Table_MedalAttribute temp = new MT_Table_MedalAttribute("MedalAttribute");
				temp.Initialize();
				mTableMedalAttribute = temp;
			} catch (Exception e) {
				TableUtil.Error("TableMedalAttribute is error : ",e);
			}
        }
        return mTableMedalAttribute;
    }
    private MT_Table_MedalSkill mTableMedalSkill = null;
    public MT_Table_MedalSkill TableMedalSkill() {
        if (mTableMedalSkill == null) {
            try {
                mTableMedalSkill = new MT_Table_MedalSkill("MedalSkill");
                mTableMedalSkill.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableMedalSkill is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("MedalSkill")){
        	try {
        		m_ModifiedTableMap.remove("MedalSkill");
				MT_Table_MedalSkill temp = new MT_Table_MedalSkill("MedalSkill");
				temp.Initialize();
				mTableMedalSkill = temp;
			} catch (Exception e) {
				TableUtil.Error("TableMedalSkill is error : ",e);
			}
        }
        return mTableMedalSkill;
    }
    private MT_Table_MedalStar mTableMedalStar = null;
    public MT_Table_MedalStar TableMedalStar() {
        if (mTableMedalStar == null) {
            try {
                mTableMedalStar = new MT_Table_MedalStar("MedalStar");
                mTableMedalStar.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableMedalStar is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("MedalStar")){
        	try {
        		m_ModifiedTableMap.remove("MedalStar");
				MT_Table_MedalStar temp = new MT_Table_MedalStar("MedalStar");
				temp.Initialize();
				mTableMedalStar = temp;
			} catch (Exception e) {
				TableUtil.Error("TableMedalStar is error : ",e);
			}
        }
        return mTableMedalStar;
    }
    private MT_Table_PassiveBuff mTablePassiveBuff = null;
    public MT_Table_PassiveBuff TablePassiveBuff() {
        if (mTablePassiveBuff == null) {
            try {
                mTablePassiveBuff = new MT_Table_PassiveBuff("PassiveBuff");
                mTablePassiveBuff.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TablePassiveBuff is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("PassiveBuff")){
        	try {
        		m_ModifiedTableMap.remove("PassiveBuff");
				MT_Table_PassiveBuff temp = new MT_Table_PassiveBuff("PassiveBuff");
				temp.Initialize();
				mTablePassiveBuff = temp;
			} catch (Exception e) {
				TableUtil.Error("TablePassiveBuff is error : ",e);
			}
        }
        return mTablePassiveBuff;
    }
    private MT_Table_PlayerConfig mTablePlayerConfig = null;
    public MT_Table_PlayerConfig TablePlayerConfig() {
        if (mTablePlayerConfig == null) {
            try {
                mTablePlayerConfig = new MT_Table_PlayerConfig("PlayerConfig");
                mTablePlayerConfig.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TablePlayerConfig is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("PlayerConfig")){
        	try {
        		m_ModifiedTableMap.remove("PlayerConfig");
				MT_Table_PlayerConfig temp = new MT_Table_PlayerConfig("PlayerConfig");
				temp.Initialize();
				mTablePlayerConfig = temp;
			} catch (Exception e) {
				TableUtil.Error("TablePlayerConfig is error : ",e);
			}
        }
        return mTablePlayerConfig;
    }
    private MT_Table_Prompt mTablePrompt = null;
    public MT_Table_Prompt TablePrompt() {
        if (mTablePrompt == null) {
            try {
                mTablePrompt = new MT_Table_Prompt("Prompt");
                mTablePrompt.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TablePrompt is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Prompt")){
        	try {
        		m_ModifiedTableMap.remove("Prompt");
				MT_Table_Prompt temp = new MT_Table_Prompt("Prompt");
				temp.Initialize();
				mTablePrompt = temp;
			} catch (Exception e) {
				TableUtil.Error("TablePrompt is error : ",e);
			}
        }
        return mTablePrompt;
    }
    private MT_Table_PushEvent mTablePushEvent = null;
    public MT_Table_PushEvent TablePushEvent() {
        if (mTablePushEvent == null) {
            try {
                mTablePushEvent = new MT_Table_PushEvent("PushEvent");
                mTablePushEvent.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TablePushEvent is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("PushEvent")){
        	try {
        		m_ModifiedTableMap.remove("PushEvent");
				MT_Table_PushEvent temp = new MT_Table_PushEvent("PushEvent");
				temp.Initialize();
				mTablePushEvent = temp;
			} catch (Exception e) {
				TableUtil.Error("TablePushEvent is error : ",e);
			}
        }
        return mTablePushEvent;
    }
    private MT_Table_PvpVirtual mTablePvpVirtual = null;
    public MT_Table_PvpVirtual TablePvpVirtual() {
        if (mTablePvpVirtual == null) {
            try {
                mTablePvpVirtual = new MT_Table_PvpVirtual("PvpVirtual");
                mTablePvpVirtual.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TablePvpVirtual is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("PvpVirtual")){
        	try {
        		m_ModifiedTableMap.remove("PvpVirtual");
				MT_Table_PvpVirtual temp = new MT_Table_PvpVirtual("PvpVirtual");
				temp.Initialize();
				mTablePvpVirtual = temp;
			} catch (Exception e) {
				TableUtil.Error("TablePvpVirtual is error : ",e);
			}
        }
        return mTablePvpVirtual;
    }
    private MT_Table_Rank mTableRank = null;
    public MT_Table_Rank TableRank() {
        if (mTableRank == null) {
            try {
                mTableRank = new MT_Table_Rank("Rank");
                mTableRank.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableRank is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Rank")){
        	try {
        		m_ModifiedTableMap.remove("Rank");
				MT_Table_Rank temp = new MT_Table_Rank("Rank");
				temp.Initialize();
				mTableRank = temp;
			} catch (Exception e) {
				TableUtil.Error("TableRank is error : ",e);
			}
        }
        return mTableRank;
    }
    private MT_Table_RechargeActive mTableRechargeActive = null;
    public MT_Table_RechargeActive TableRechargeActive() {
        if (mTableRechargeActive == null) {
            try {
                mTableRechargeActive = new MT_Table_RechargeActive("RechargeActive");
                mTableRechargeActive.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableRechargeActive is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("RechargeActive")){
        	try {
        		m_ModifiedTableMap.remove("RechargeActive");
				MT_Table_RechargeActive temp = new MT_Table_RechargeActive("RechargeActive");
				temp.Initialize();
				mTableRechargeActive = temp;
			} catch (Exception e) {
				TableUtil.Error("TableRechargeActive is error : ",e);
			}
        }
        return mTableRechargeActive;
    }
    private MT_Table_Skill mTableSkill = null;
    public MT_Table_Skill TableSkill() {
        if (mTableSkill == null) {
            try {
                mTableSkill = new MT_Table_Skill("Skill");
                mTableSkill.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableSkill is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Skill")){
        	try {
        		m_ModifiedTableMap.remove("Skill");
				MT_Table_Skill temp = new MT_Table_Skill("Skill");
				temp.Initialize();
				mTableSkill = temp;
			} catch (Exception e) {
				TableUtil.Error("TableSkill is error : ",e);
			}
        }
        return mTableSkill;
    }
    private MT_Table_StringMessage mTableStringMessage = null;
    public MT_Table_StringMessage TableStringMessage() {
        if (mTableStringMessage == null) {
            try {
                mTableStringMessage = new MT_Table_StringMessage("StringMessage");
                mTableStringMessage.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableStringMessage is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("StringMessage")){
        	try {
        		m_ModifiedTableMap.remove("StringMessage");
				MT_Table_StringMessage temp = new MT_Table_StringMessage("StringMessage");
				temp.Initialize();
				mTableStringMessage = temp;
			} catch (Exception e) {
				TableUtil.Error("TableStringMessage is error : ",e);
			}
        }
        return mTableStringMessage;
    }
    private MT_Table_Task mTableTask = null;
    public MT_Table_Task TableTask() {
        if (mTableTask == null) {
            try {
                mTableTask = new MT_Table_Task("Task");
                mTableTask.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableTask is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Task")){
        	try {
        		m_ModifiedTableMap.remove("Task");
				MT_Table_Task temp = new MT_Table_Task("Task");
				temp.Initialize();
				mTableTask = temp;
			} catch (Exception e) {
				TableUtil.Error("TableTask is error : ",e);
			}
        }
        return mTableTask;
    }
    private MT_Table_TaskLimit mTableTaskLimit = null;
    public MT_Table_TaskLimit TableTaskLimit() {
        if (mTableTaskLimit == null) {
            try {
                mTableTaskLimit = new MT_Table_TaskLimit("TaskLimit");
                mTableTaskLimit.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableTaskLimit is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("TaskLimit")){
        	try {
        		m_ModifiedTableMap.remove("TaskLimit");
				MT_Table_TaskLimit temp = new MT_Table_TaskLimit("TaskLimit");
				temp.Initialize();
				mTableTaskLimit = temp;
			} catch (Exception e) {
				TableUtil.Error("TableTaskLimit is error : ",e);
			}
        }
        return mTableTaskLimit;
    }
    private MT_Table_TeachInstance mTableTeachInstance = null;
    public MT_Table_TeachInstance TableTeachInstance() {
        if (mTableTeachInstance == null) {
            try {
                mTableTeachInstance = new MT_Table_TeachInstance("TeachInstance");
                mTableTeachInstance.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableTeachInstance is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("TeachInstance")){
        	try {
        		m_ModifiedTableMap.remove("TeachInstance");
				MT_Table_TeachInstance temp = new MT_Table_TeachInstance("TeachInstance");
				temp.Initialize();
				mTableTeachInstance = temp;
			} catch (Exception e) {
				TableUtil.Error("TableTeachInstance is error : ",e);
			}
        }
        return mTableTeachInstance;
    }
    private MT_Table_TeachShadow mTableTeachShadow = null;
    public MT_Table_TeachShadow TableTeachShadow() {
        if (mTableTeachShadow == null) {
            try {
                mTableTeachShadow = new MT_Table_TeachShadow("TeachShadow");
                mTableTeachShadow.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableTeachShadow is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("TeachShadow")){
        	try {
        		m_ModifiedTableMap.remove("TeachShadow");
				MT_Table_TeachShadow temp = new MT_Table_TeachShadow("TeachShadow");
				temp.Initialize();
				mTableTeachShadow = temp;
			} catch (Exception e) {
				TableUtil.Error("TableTeachShadow is error : ",e);
			}
        }
        return mTableTeachShadow;
    }
    private MT_Table_TechInfo mTableTechInfo = null;
    public MT_Table_TechInfo TableTechInfo() {
        if (mTableTechInfo == null) {
            try {
                mTableTechInfo = new MT_Table_TechInfo("TechInfo");
                mTableTechInfo.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableTechInfo is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("TechInfo")){
        	try {
        		m_ModifiedTableMap.remove("TechInfo");
				MT_Table_TechInfo temp = new MT_Table_TechInfo("TechInfo");
				temp.Initialize();
				mTableTechInfo = temp;
			} catch (Exception e) {
				TableUtil.Error("TableTechInfo is error : ",e);
			}
        }
        return mTableTechInfo;
    }
    private MT_Table_TimeReward mTableTimeReward = null;
    public MT_Table_TimeReward TableTimeReward() {
        if (mTableTimeReward == null) {
            try {
                mTableTimeReward = new MT_Table_TimeReward("TimeReward");
                mTableTimeReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableTimeReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("TimeReward")){
        	try {
        		m_ModifiedTableMap.remove("TimeReward");
				MT_Table_TimeReward temp = new MT_Table_TimeReward("TimeReward");
				temp.Initialize();
				mTableTimeReward = temp;
			} catch (Exception e) {
				TableUtil.Error("TableTimeReward is error : ",e);
			}
        }
        return mTableTimeReward;
    }
    private MT_Table_RotaryReward mTableRotaryReward = null;
    public MT_Table_RotaryReward TableRotaryReward() {
        if (mTableRotaryReward == null) {
            try {
            	mTableRotaryReward = new MT_Table_RotaryReward("RotaryReward");
            	mTableRotaryReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableRotaryReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("RotaryReward")){
        	try {
        		m_ModifiedTableMap.remove("RotaryReward");
        		MT_Table_RotaryReward temp = new MT_Table_RotaryReward("RotaryReward");
        		temp.Initialize();
        		mTableRotaryReward = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableRotaryReward is error : ",e);
			}
        }
        return mTableRotaryReward;
    }
    private MT_Table_GItem mTableGItem = null;
    public MT_Table_GItem TableGItem() {
        if (mTableGItem == null) {
            try {
            	mTableGItem = new MT_Table_GItem("GItem");
            	mTableGItem.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGItem is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GItem")){
        	try {
        		m_ModifiedTableMap.remove("GItem");
        		MT_Table_GItem temp = new MT_Table_GItem("GItem");
        		temp.Initialize();
        		mTableGItem = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGItem is error : ",e);
			}
        }
        return mTableGItem;
    }
    private MT_Table_GCash mTableGCash = null;
    public MT_Table_GCash TableGCash() {
        if (mTableGCash == null) {
            try {
            	mTableGCash = new MT_Table_GCash("GCash");
            	mTableGCash.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGCash is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GCash")){
        	try {
        		m_ModifiedTableMap.remove("GCash");
        		MT_Table_GCash temp = new MT_Table_GCash("GCash");
        		temp.Initialize();
        		mTableGCash = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGCash is error : ",e);
			}
        }
        return mTableGCash;
    }
    private MT_Table_Lotty mTableLotty = null;
    public MT_Table_Lotty TableLotty() {
        if (mTableLotty == null) {
            try {
            	mTableLotty = new MT_Table_Lotty("Lotty");
            	mTableLotty.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableLotty is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Lotty")){
        	try {
        		m_ModifiedTableMap.remove("Lotty");
        		MT_Table_Lotty temp = new MT_Table_Lotty("Lotty");
        		temp.Initialize();
        		mTableLotty = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableLotty is error : ",e);
			}
        }
        return mTableLotty;
    }
    private MT_Table_GExp mTableGExp = null;
    public MT_Table_GExp TableGExp() {
        if (mTableGExp == null) {
            try {
            	mTableGExp = new MT_Table_GExp("GExp");
            	mTableGExp.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGExp is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GExp")){
        	try {
        		m_ModifiedTableMap.remove("GExp");
        		MT_Table_GExp temp = new MT_Table_GExp("GExp");
        		temp.Initialize();
        		mTableGExp = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGExp is error : ",e);
			}
        }
        return mTableGExp;
    }
    private MT_Table_GCost mTableGCost = null;
    public MT_Table_GCost TableGCost() {
        if (mTableGCost == null) {
            try {
            	mTableGCost = new MT_Table_GCost("GCost");
            	mTableGCost.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGCost is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GCost")){
        	try {
        		m_ModifiedTableMap.remove("GCost");
        		MT_Table_GCost temp = new MT_Table_GCost("GCost");
        		temp.Initialize();
        		mTableGCost = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGCost is error : ",e);
			}
        }
        return mTableGCost;
    }
    private MT_Table_GCLoginReward mTableGCLoginReward = null;
    public MT_Table_GCLoginReward TableGCLoginReward() {
        if (mTableGCLoginReward == null) {
            try {
            	mTableGCLoginReward = new MT_Table_GCLoginReward("GCLoginReward");
            	mTableGCLoginReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGCLoginReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GCLoginReward")){
        	try {
        		m_ModifiedTableMap.remove("GCLoginReward");
        		MT_Table_GCLoginReward temp = new MT_Table_GCLoginReward("GCLoginReward");
        		temp.Initialize();
        		mTableGCLoginReward = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGCLoginReward is error : ",e);
			}
        }
        return mTableGCLoginReward;
    }
    private MT_Table_GVip mTableGVip = null;
    public MT_Table_GVip TableGVip() {
        if (mTableGVip == null) {
            try {
            	mTableGVip = new MT_Table_GVip("GVip");
            	mTableGVip.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGVip is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GVip")){
        	try {
        		m_ModifiedTableMap.remove("GVip");
        		MT_Table_GVip temp = new MT_Table_GVip("GVip");
        		temp.Initialize();
        		mTableGVip = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGVip is error : ",e);
			}
        }
        return mTableGVip;
    }
    private MT_Table_GRechargePolicy mTableGRechargePolicy = null;
    public MT_Table_GRechargePolicy TableGRechargePolicy() {
        if (mTableGRechargePolicy == null) {
            try {
            	mTableGRechargePolicy = new MT_Table_GRechargePolicy("GRechargePolicy");
            	mTableGRechargePolicy.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGRechargePolicy is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GRechargePolicy")){
        	try {
        		m_ModifiedTableMap.remove("GRechargePolicy");
        		MT_Table_GRechargePolicy temp = new MT_Table_GRechargePolicy("GRechargePolicy");
        		temp.Initialize();
        		mTableGRechargePolicy = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGRechargePolicy is error : ",e);
			}
        }
        return mTableGRechargePolicy;
    }
    private MT_Table_GSlotsJZActiveReward mTableGSlotsJZActiveReward = null;
    public MT_Table_GSlotsJZActiveReward TableGSlotsJZActiveReward() {
        if (mTableGSlotsJZActiveReward == null) {
            try {
            	mTableGSlotsJZActiveReward = new MT_Table_GSlotsJZActiveReward("GSlotsJZActiveReward");
            	mTableGSlotsJZActiveReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsJZActiveReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsJZActiveReward")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsJZActiveReward");
        		MT_Table_GSlotsJZActiveReward temp = new MT_Table_GSlotsJZActiveReward("GSlotsJZActiveReward");
        		temp.Initialize();
        		mTableGSlotsJZActiveReward = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsJZActiveReward is error : ",e);
			}
        }
        return mTableGSlotsJZActiveReward;
    }
    private MT_Table_GSlotsFriutActiveReward mTableGSlotsFriutActiveReward = null;
    public MT_Table_GSlotsFriutActiveReward TableGSlotsFriutActiveReward() {
        if (mTableGSlotsFriutActiveReward == null) {
            try {
            	mTableGSlotsFriutActiveReward = new MT_Table_GSlotsFriutActiveReward("GSlotsFriutActiveReward");
            	mTableGSlotsFriutActiveReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsFriutActiveReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsFriutActiveReward")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsFriutActiveReward");
        		MT_Table_GSlotsFriutActiveReward temp = new MT_Table_GSlotsFriutActiveReward("GSlotsFriutActiveReward");
        		temp.Initialize();
        		mTableGSlotsFriutActiveReward = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsFriutActiveReward is error : ",e);
			}
        }
        return mTableGSlotsFriutActiveReward;
    }
    private MT_Table_GRechargeBack mTableGRechargeBack = null;
    public MT_Table_GRechargeBack TableGRechargeBack() {
        if (mTableGRechargeBack == null) {
            try {
            	mTableGRechargeBack = new MT_Table_GRechargeBack("GRechargeBack");
            	mTableGRechargeBack.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGRechargeBack is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GRechargeBack")){
        	try {
        		m_ModifiedTableMap.remove("GRechargeBack");
        		MT_Table_GRechargeBack temp = new MT_Table_GRechargeBack("GRechargeBack");
        		temp.Initialize();
        		mTableGRechargeBack = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGRechargeBack is error : ",e);
			}
        }
        return mTableGRechargeBack;
    }
    private MT_Table_GSlotsMoney mTableGSlotsMoney = null;
    public MT_Table_GSlotsMoney TableGSlotsMoney() {
        if (mTableGSlotsMoney == null) {
            try {
            	mTableGSlotsMoney = new MT_Table_GSlotsMoney("GSlotsMoney");
            	mTableGSlotsMoney.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsMoney is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsMoney")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsMoney");
        		MT_Table_GSlotsMoney temp = new MT_Table_GSlotsMoney("GSlotsMoney");
        		temp.Initialize();
        		mTableGSlotsMoney = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsMoney is error : ",e);
			}
        }
        return mTableGSlotsMoney;
    }
    private MT_Table_GSlotsLotty mTableGSlotsLotty = null;
    public MT_Table_GSlotsLotty TableGSlotsLotty() {
        if (mTableGSlotsLotty == null) {
            try {
            	mTableGSlotsLotty = new MT_Table_GSlotsLotty("GSlotsLotty");
            	mTableGSlotsLotty.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsLotty is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsLotty")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsLotty");
        		MT_Table_GSlotsLotty temp = new MT_Table_GSlotsLotty("GSlotsLotty");
        		temp.Initialize();
        		mTableGSlotsLotty = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsLotty is error : ",e);
			}
        }
        return mTableGSlotsLotty;
    }
    private MT_Table_GSlotsOpen mTableGSlotsOpen = null;
    public MT_Table_GSlotsOpen TableGSlotsOpen() {
        if (mTableGSlotsOpen == null) {
            try {
            	mTableGSlotsOpen = new MT_Table_GSlotsOpen("GSlotsOpen");
            	mTableGSlotsOpen.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsOpen is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsOpen")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsOpen");
        		MT_Table_GSlotsOpen temp = new MT_Table_GSlotsOpen("GSlotsOpen");
        		temp.Initialize();
        		mTableGSlotsOpen = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsOpen is error : ",e);
			}
        }
        return mTableGSlotsOpen;
    }
    private MT_Table_GSlotsGuess mTableGSlotsGuess = null;
    public MT_Table_GSlotsGuess TableGSlotsGuess() {
        if (mTableGSlotsGuess == null) {
            try {
            	mTableGSlotsGuess = new MT_Table_GSlotsGuess("GSlotsGuess");
            	mTableGSlotsGuess.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsGuess is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsGuess")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsGuess");
        		MT_Table_GSlotsGuess temp = new MT_Table_GSlotsGuess("GSlotsGuess");
        		temp.Initialize();
        		mTableGSlotsGuess = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsGuess is error : ",e);
			}
        }
        return mTableGSlotsGuess;
    }
    private MT_Table_GFSkill mTableGFSkill = null;
    public MT_Table_GFSkill TableGFSkill() {
        if (mTableGFSkill == null) {
            try {
            	mTableGFSkill = new MT_Table_GFSkill("GFSkill");
            	mTableGFSkill.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGFSkill is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GFSkill")){
        	try {
        		m_ModifiedTableMap.remove("GFSkill");
        		MT_Table_GFSkill temp = new MT_Table_GFSkill("GFSkill");
        		temp.Initialize();
        		mTableGFSkill = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGFSkill is error : ",e);
			}
        }
        return mTableGFSkill;
    }
    private MT_Table_GFBullet mTableGFBullet = null;
    public MT_Table_GFBullet TableGFBullet() {
        if (mTableGFBullet == null) {
            try {
            	mTableGFBullet = new MT_Table_GFBullet("GFBullet");
            	mTableGFBullet.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGFBullet is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GFBullet")){
        	try {
        		m_ModifiedTableMap.remove("GFBullet");
        		MT_Table_GFBullet temp = new MT_Table_GFBullet("GFBullet");
        		temp.Initialize();
        		mTableGFBullet = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGFBullet is error : ",e);
			}
        }
        return mTableGFBullet;
    }
    private MT_Table_GFCannon mTableGFCannon = null;
    public MT_Table_GFCannon TableGFCannon() {
        if (mTableGFCannon == null) {
            try {
            	mTableGFCannon = new MT_Table_GFCannon("GFCannon");
            	mTableGFCannon.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGFCannon is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GFCannon")){
        	try {
        		m_ModifiedTableMap.remove("GFCannon");
        		MT_Table_GFCannon temp = new MT_Table_GFCannon("GFCannon");
        		temp.Initialize();
        		mTableGFCannon = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGFCannon is error : ",e);
			}
        }
        return mTableGFCannon;
    }
    private MT_Table_GStoreItem mTableGStoreItem = null;
    public MT_Table_GStoreItem TableGStoreItem() {
        if (mTableGStoreItem == null) {
            try {
            	mTableGStoreItem = new MT_Table_GStoreItem("GStoreItem");
            	mTableGStoreItem.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGStoreItem is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GStoreItem")){
        	try {
        		m_ModifiedTableMap.remove("GStoreItem");
        		MT_Table_GStoreItem temp = new MT_Table_GStoreItem("GStoreItem");
        		temp.Initialize();
        		mTableGStoreItem = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGStoreItem is error : ",e);
			}
        }
        return mTableGStoreItem;
    }
    private MT_Table_GGiftPackageShop mTableGGiftPackageShop = null;
    public MT_Table_GGiftPackageShop TableGGiftPackageShop() {
        if (mTableGGiftPackageShop == null) {
            try {
            	mTableGGiftPackageShop = new MT_Table_GGiftPackageShop("GGiftPackageShop");
            	mTableGGiftPackageShop.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGGiftPackageShop is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GGiftPackageShop")){
        	try {
        		m_ModifiedTableMap.remove("GGiftPackageShop");
        		MT_Table_GGiftPackageShop temp = new MT_Table_GGiftPackageShop("GGiftPackageShop");
        		temp.Initialize();
        		mTableGGiftPackageShop = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGGiftPackageShop is error : ",e);
			}
        }
        return mTableGGiftPackageShop;
    }
    private MT_Table_GFRandom mTableGFRandom = null;
    public MT_Table_GFRandom TableGFRandom() {
        if (mTableGFRandom == null) {
            try {
            	mTableGFRandom = new MT_Table_GFRandom("GFRandom");
            	mTableGFRandom.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGFRandom is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GFRandom")){
        	try {
        		m_ModifiedTableMap.remove("GFRandom");
        		MT_Table_GFRandom temp = new MT_Table_GFRandom("GFRandom");
        		temp.Initialize();
        		mTableGFRandom = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGFRandom is error : ",e);
			}
        }
        return mTableGFRandom;
    }
    private MT_Table_GRankReward mTableGRankReward = null;
    public MT_Table_GRankReward TableGRankReward() {
        if (mTableGRankReward == null) {
            try {
            	mTableGRankReward = new MT_Table_GRankReward("GRankReward");
            	mTableGRankReward.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGRankReward is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GRankReward")){
        	try {
        		m_ModifiedTableMap.remove("GRankReward");
        		MT_Table_GRankReward temp = new MT_Table_GRankReward("GRankReward");
        		temp.Initialize();
        		mTableGRankReward = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGRankReward is error : ",e);
			}
        }
        return mTableGRankReward;
    }
    private MT_Table_GSlotsLine mTableGSlotsLine = null;
    public MT_Table_GSlotsLine TableGSlotsLine() {
        if (mTableGSlotsLine == null) {
            try {
            	mTableGSlotsLine = new MT_Table_GSlotsLine("GSlotsLine");
            	mTableGSlotsLine.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsLine is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsLine")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsLine");
        		MT_Table_GSlotsLine temp = new MT_Table_GSlotsLine("GSlotsLine");
        		temp.Initialize();
        		mTableGSlotsLine = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsLine is error : ",e);
			}
        }
        return mTableGSlotsLine;
    }
    private MT_Table_GSlotsRate mTableGSlotsRate = null;
    public MT_Table_GSlotsRate TableGSlotsRate() {
        if (mTableGSlotsRate == null) {
            try {
            	mTableGSlotsRate = new MT_Table_GSlotsRate("GSlotsRate");
            	mTableGSlotsRate.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsRate is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsRate")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsRate");
        		MT_Table_GSlotsRate temp = new MT_Table_GSlotsRate("GSlotsRate");
        		temp.Initialize();
        		mTableGSlotsRate = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsRate is error : ",e);
			}
        }
        return mTableGSlotsRate;
    }
    private MT_Table_GSlotsRandom mTableGSlotsRandom = null;
    public MT_Table_GSlotsRandom TableGSlotsRandom() {
        if (mTableGSlotsRandom == null) {
            try {
            	mTableGSlotsRandom = new MT_Table_GSlotsRandom("GSlotsRandom");
            	mTableGSlotsRandom.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGSlotsRandom is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GSlotsRandom")){
        	try {
        		m_ModifiedTableMap.remove("GSlotsRandom");
        		MT_Table_GSlotsRandom temp = new MT_Table_GSlotsRandom("GSlotsRandom");
        		temp.Initialize();
        		mTableGSlotsRandom = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGSlotsRandom is error : ",e);
			}
        }
        return mTableGSlotsRandom;
    }
    private MT_Table_GGiftShop mTableGGiftShop = null;
    public MT_Table_GGiftShop TableGGiftShop() {
        if (mTableGGiftShop == null) {
            try {
            	mTableGGiftShop = new MT_Table_GGiftShop("GGiftShop");
            	mTableGGiftShop.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGGiftShop is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GGiftShop")){
        	try {
        		m_ModifiedTableMap.remove("GGiftShop");
        		MT_Table_GGiftShop temp = new MT_Table_GGiftShop("GGiftShop");
        		temp.Initialize();
        		mTableGGiftShop = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGGiftShop is error : ",e);
			}
        }
        return mTableGGiftShop;
    }
    private MT_Table_GLevelShop mTableGLevelShop = null;
    public MT_Table_GLevelShop TableGLevelShop() {
        if (mTableGLevelShop == null) {
            try {
            	mTableGLevelShop = new MT_Table_GLevelShop("GLevelShop");
            	mTableGLevelShop.Initialize();
            } catch (Exception e) {
                TableUtil.Error("mTableGLevelShop is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("GLevelShop")){
        	try {
        		m_ModifiedTableMap.remove("GLevelShop");
        		MT_Table_GLevelShop temp = new MT_Table_GLevelShop("GLevelShop");
        		temp.Initialize();
        		mTableGLevelShop = temp;
			} catch (Exception e) {
				TableUtil.Error("mTableGLevelShop is error : ",e);
			}
        }
        return mTableGLevelShop;
    }
    private MT_Table_TimeRewardConfig mTableTimeRewardConfig = null;
    public MT_Table_TimeRewardConfig TableTimeRewardConfig() {
        if (mTableTimeRewardConfig == null) {
            try {
                mTableTimeRewardConfig = new MT_Table_TimeRewardConfig("TimeRewardConfig");
                mTableTimeRewardConfig.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableTimeRewardConfig is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("TimeRewardConfig")){
        	try {
        		m_ModifiedTableMap.remove("TimeRewardConfig");
				MT_Table_TimeRewardConfig temp = new MT_Table_TimeRewardConfig("TimeRewardConfig");
				temp.Initialize();
				mTableTimeRewardConfig = temp;
			} catch (Exception e) {
				TableUtil.Error("TableTimeRewardConfig is error : ",e);
			}
        }
        return mTableTimeRewardConfig;
    }
    private MT_Table_UIConditions mTableUIConditions = null;
    public MT_Table_UIConditions TableUIConditions() {
        if (mTableUIConditions == null) {
            try {
                mTableUIConditions = new MT_Table_UIConditions("UIConditions");
                mTableUIConditions.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableUIConditions is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("UIConditions")){
        	try {
        		m_ModifiedTableMap.remove("UIConditions");
				MT_Table_UIConditions temp = new MT_Table_UIConditions("UIConditions");
				temp.Initialize();
				mTableUIConditions = temp;
			} catch (Exception e) {
				TableUtil.Error("TableUIConditions is error : ",e);
			}
        }
        return mTableUIConditions;
    }
    private MT_Table_UserInterface mTableUserInterface = null;
    public MT_Table_UserInterface TableUserInterface() {
        if (mTableUserInterface == null) {
            try {
                mTableUserInterface = new MT_Table_UserInterface("UserInterface");
                mTableUserInterface.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableUserInterface is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("UserInterface")){
        	try {
        		m_ModifiedTableMap.remove("UserInterface");
				MT_Table_UserInterface temp = new MT_Table_UserInterface("UserInterface");
				temp.Initialize();
				mTableUserInterface = temp;
			} catch (Exception e) {
				TableUtil.Error("TableUserInterface is error : ",e);
			}
        }
        return mTableUserInterface;
    }
    private MT_Table_Vip mTableVip = null;
    public MT_Table_Vip TableVip() {
        if (mTableVip == null) {
            try {
                mTableVip = new MT_Table_Vip("Vip");
                mTableVip.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableVip is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("Vip")){
        	try {
        		m_ModifiedTableMap.remove("Vip");
				MT_Table_Vip temp = new MT_Table_Vip("Vip");
				temp.Initialize();
				mTableVip = temp;
			} catch (Exception e) {
				TableUtil.Error("TableVip is error : ",e);
			}
        }
        return mTableVip;
    }
    private MT_Table_WallLayout mTableWallLayout = null;
    public MT_Table_WallLayout TableWallLayout() {
        if (mTableWallLayout == null) {
            try {
                mTableWallLayout = new MT_Table_WallLayout("WallLayout");
                mTableWallLayout.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableWallLayout is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("WallLayout")){
        	try {
        		m_ModifiedTableMap.remove("WallLayout");
				MT_Table_WallLayout temp = new MT_Table_WallLayout("WallLayout");
				temp.Initialize();
				mTableWallLayout = temp;
			} catch (Exception e) {
				TableUtil.Error("TableWallLayout is error : ",e);
			}
        }
        return mTableWallLayout;
    }
    private MT_Table_WantedInstance mTableWantedInstance = null;
    public MT_Table_WantedInstance TableWantedInstance() {
        if (mTableWantedInstance == null) {
            try {
                mTableWantedInstance = new MT_Table_WantedInstance("WantedInstance");
                mTableWantedInstance.Initialize();
            } catch (Exception e) {
                TableUtil.Error("TableWantedInstance is error : ",e);
            }
        }
		else if (m_ModifiedTableMap.containsKey("WantedInstance")){
        	try {
        		m_ModifiedTableMap.remove("WantedInstance");
				MT_Table_WantedInstance temp = new MT_Table_WantedInstance("WantedInstance");
				temp.Initialize();
				mTableWantedInstance = temp;
			} catch (Exception e) {
				TableUtil.Error("TableWantedInstance is error : ",e);
			}
        }
        return mTableWantedInstance;
    }
    public enum AirBase {
        AirBase_airsupport,
    }
    private HashMap<String,MT_Table_AirBase> AirBaseArray = new HashMap<String,MT_Table_AirBase>();
    public MT_Table_AirBase getSpawns(AirBase index) {
        if (AirBaseArray.containsKey(index.toString())) {
			MT_Table_AirBase spawns = AirBaseArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(AirBase index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_AirBase spawns = new MT_Table_AirBase(index.toString());
	        spawns.Initialize();
	        AirBaseArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(AirBase index) is error : ",e);
        }
        AirBaseArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_AirBase getSpawns_AirBase(String index) {
        String str = "AirBase_" + index;
        if (AirBaseArray.containsKey(str)) {
            return AirBaseArray.get(str);
        }
        try {
            MT_Table_AirBase spawns = new MT_Table_AirBase(str);
            spawns.Initialize();
            AirBaseArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_AirBase(String index) is error : ",e);
        }
        AirBaseArray.put(str, null);
	    return null;
    }
    public enum assets {
        assets_Money,
        assets_Rare,
    }
    private HashMap<String,MT_Table_assets> assetsArray = new HashMap<String,MT_Table_assets>();
    public MT_Table_assets getSpawns(assets index) {
        if (assetsArray.containsKey(index.toString())) {
			MT_Table_assets spawns = assetsArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(assets index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_assets spawns = new MT_Table_assets(index.toString());
	        spawns.Initialize();
	        assetsArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(assets index) is error : ",e);
        }
        assetsArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_assets getSpawns_assets(String index) {
        String str = "assets_" + index;
        if (assetsArray.containsKey(str)) {
            return assetsArray.get(str);
        }
        try {
            MT_Table_assets spawns = new MT_Table_assets(str);
            spawns.Initialize();
            assetsArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_assets(String index) is error : ",e);
        }
        assetsArray.put(str, null);
	    return null;
    }
    public enum depot {
        depot_Money,
    }
    private HashMap<String,MT_Table_depot> depotArray = new HashMap<String,MT_Table_depot>();
    public MT_Table_depot getSpawns(depot index) {
        if (depotArray.containsKey(index.toString())) {
			MT_Table_depot spawns = depotArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(depot index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_depot spawns = new MT_Table_depot(index.toString());
	        spawns.Initialize();
	        depotArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(depot index) is error : ",e);
        }
        depotArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_depot getSpawns_depot(String index) {
        String str = "depot_" + index;
        if (depotArray.containsKey(str)) {
            return depotArray.get(str);
        }
        try {
            MT_Table_depot spawns = new MT_Table_depot(str);
            spawns.Initialize();
            depotArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_depot(String index) is error : ",e);
        }
        depotArray.put(str, null);
	    return null;
    }
    public enum EquipB {
        EquipB_Build,
    }
    private HashMap<String,MT_Table_EquipB> EquipBArray = new HashMap<String,MT_Table_EquipB>();
    public MT_Table_EquipB getSpawns(EquipB index) {
        if (EquipBArray.containsKey(index.toString())) {
			MT_Table_EquipB spawns = EquipBArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(EquipB index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_EquipB spawns = new MT_Table_EquipB(index.toString());
	        spawns.Initialize();
	        EquipBArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(EquipB index) is error : ",e);
        }
        EquipBArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_EquipB getSpawns_EquipB(String index) {
        String str = "EquipB_" + index;
        if (EquipBArray.containsKey(str)) {
            return EquipBArray.get(str);
        }
        try {
            MT_Table_EquipB spawns = new MT_Table_EquipB(str);
            spawns.Initialize();
            EquipBArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_EquipB(String index) is error : ",e);
        }
        EquipBArray.put(str, null);
	    return null;
    }
    public enum factory {
        factory_Arsenal,
        factory_gaojibing,
        factory_tanke,
    }
    private HashMap<String,MT_Table_factory> factoryArray = new HashMap<String,MT_Table_factory>();
    public MT_Table_factory getSpawns(factory index) {
        if (factoryArray.containsKey(index.toString())) {
			MT_Table_factory spawns = factoryArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(factory index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_factory spawns = new MT_Table_factory(index.toString());
	        spawns.Initialize();
	        factoryArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(factory index) is error : ",e);
        }
        factoryArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_factory getSpawns_factory(String index) {
        String str = "factory_" + index;
        if (factoryArray.containsKey(str)) {
            return factoryArray.get(str);
        }
        try {
            MT_Table_factory spawns = new MT_Table_factory(str);
            spawns.Initialize();
            factoryArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_factory(String index) is error : ",e);
        }
        factoryArray.put(str, null);
	    return null;
    }
    public enum flag {
        flag_BattleFlag,
    }
    private HashMap<String,MT_Table_flag> flagArray = new HashMap<String,MT_Table_flag>();
    public MT_Table_flag getSpawns(flag index) {
        if (flagArray.containsKey(index.toString())) {
			MT_Table_flag spawns = flagArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(flag index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_flag spawns = new MT_Table_flag(index.toString());
	        spawns.Initialize();
	        flagArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(flag index) is error : ",e);
        }
        flagArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_flag getSpawns_flag(String index) {
        String str = "flag_" + index;
        if (flagArray.containsKey(str)) {
            return flagArray.get(str);
        }
        try {
            MT_Table_flag spawns = new MT_Table_flag(str);
            spawns.Initialize();
            flagArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_flag(String index) is error : ",e);
        }
        flagArray.put(str, null);
	    return null;
    }
    public enum institute {
        institute_Arsenal,
        institute_gaojibing,
        institute_tanke,
    }
    private HashMap<String,MT_Table_institute> instituteArray = new HashMap<String,MT_Table_institute>();
    public MT_Table_institute getSpawns(institute index) {
        if (instituteArray.containsKey(index.toString())) {
			MT_Table_institute spawns = instituteArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(institute index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_institute spawns = new MT_Table_institute(index.toString());
	        spawns.Initialize();
	        instituteArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(institute index) is error : ",e);
        }
        instituteArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_institute getSpawns_institute(String index) {
        String str = "institute_" + index;
        if (instituteArray.containsKey(str)) {
            return instituteArray.get(str);
        }
        try {
            MT_Table_institute spawns = new MT_Table_institute(str);
            spawns.Initialize();
            instituteArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_institute(String index) is error : ",e);
        }
        instituteArray.put(str, null);
	    return null;
    }
    public enum Language {
        Language_Chinese_Simplified,
        Language_EN,
    }
    private HashMap<String,MT_Table_Language> LanguageArray = new HashMap<String,MT_Table_Language>();
    public MT_Table_Language getSpawns(Language index) {
        if (LanguageArray.containsKey(index.toString())) {
			MT_Table_Language spawns = LanguageArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(Language index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_Language spawns = new MT_Table_Language(index.toString());
	        spawns.Initialize();
	        LanguageArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(Language index) is error : ",e);
        }
        LanguageArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_Language getSpawns_Language(String index) {
        String str = "Language_" + index;
        if (LanguageArray.containsKey(str)) {
            return LanguageArray.get(str);
        }
        try {
            MT_Table_Language spawns = new MT_Table_Language(str);
            spawns.Initialize();
            LanguageArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_Language(String index) is error : ",e);
        }
        LanguageArray.put(str, null);
	    return null;
    }
    public enum LegionBuild {
        LegionBuild_Build,
    }
    private HashMap<String,MT_Table_LegionBuild> LegionBuildArray = new HashMap<String,MT_Table_LegionBuild>();
    public MT_Table_LegionBuild getSpawns(LegionBuild index) {
        if (LegionBuildArray.containsKey(index.toString())) {
			MT_Table_LegionBuild spawns = LegionBuildArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(LegionBuild index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_LegionBuild spawns = new MT_Table_LegionBuild(index.toString());
	        spawns.Initialize();
	        LegionBuildArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(LegionBuild index) is error : ",e);
        }
        LegionBuildArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_LegionBuild getSpawns_LegionBuild(String index) {
        String str = "LegionBuild_" + index;
        if (LegionBuildArray.containsKey(str)) {
            return LegionBuildArray.get(str);
        }
        try {
            MT_Table_LegionBuild spawns = new MT_Table_LegionBuild(str);
            spawns.Initialize();
            LegionBuildArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_LegionBuild(String index) is error : ",e);
        }
        LegionBuildArray.put(str, null);
	    return null;
    }
    public enum main {
        main_MainCity,
    }
    private HashMap<String,MT_Table_main> mainArray = new HashMap<String,MT_Table_main>();
    public MT_Table_main getSpawns(main index) {
        if (mainArray.containsKey(index.toString())) {
			MT_Table_main spawns = mainArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(main index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_main spawns = new MT_Table_main(index.toString());
	        spawns.Initialize();
	        mainArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(main index) is error : ",e);
        }
        mainArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_main getSpawns_main(String index) {
        String str = "main_" + index;
        if (mainArray.containsKey(str)) {
            return mainArray.get(str);
        }
        try {
            MT_Table_main spawns = new MT_Table_main(str);
            spawns.Initialize();
            mainArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_main(String index) is error : ",e);
        }
        mainArray.put(str, null);
	    return null;
    }
    public enum MedalData {
        MedalData_Build,
    }
    private HashMap<String,MT_Table_MedalData> MedalDataArray = new HashMap<String,MT_Table_MedalData>();
    public MT_Table_MedalData getSpawns(MedalData index) {
        if (MedalDataArray.containsKey(index.toString())) {
			MT_Table_MedalData spawns = MedalDataArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(MedalData index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_MedalData spawns = new MT_Table_MedalData(index.toString());
	        spawns.Initialize();
	        MedalDataArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(MedalData index) is error : ",e);
        }
        MedalDataArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_MedalData getSpawns_MedalData(String index) {
        String str = "MedalData_" + index;
        if (MedalDataArray.containsKey(str)) {
            return MedalDataArray.get(str);
        }
        try {
            MT_Table_MedalData spawns = new MT_Table_MedalData(str);
            spawns.Initialize();
            MedalDataArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_MedalData(String index) is error : ",e);
        }
        MedalDataArray.put(str, null);
	    return null;
    }
    public enum Queue {
        Queue_Build,
    }
    private HashMap<String,MT_Table_Queue> QueueArray = new HashMap<String,MT_Table_Queue>();
    public MT_Table_Queue getSpawns(Queue index) {
        if (QueueArray.containsKey(index.toString())) {
			MT_Table_Queue spawns = QueueArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(Queue index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_Queue spawns = new MT_Table_Queue(index.toString());
	        spawns.Initialize();
	        QueueArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(Queue index) is error : ",e);
        }
        QueueArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_Queue getSpawns_Queue(String index) {
        String str = "Queue_" + index;
        if (QueueArray.containsKey(str)) {
            return QueueArray.get(str);
        }
        try {
            MT_Table_Queue spawns = new MT_Table_Queue(str);
            spawns.Initialize();
            QueueArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_Queue(String index) is error : ",e);
        }
        QueueArray.put(str, null);
	    return null;
    }
    public enum Rampart {
        Rampart_wall,
    }
    private HashMap<String,MT_Table_Rampart> RampartArray = new HashMap<String,MT_Table_Rampart>();
    public MT_Table_Rampart getSpawns(Rampart index) {
        if (RampartArray.containsKey(index.toString())) {
			MT_Table_Rampart spawns = RampartArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(Rampart index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_Rampart spawns = new MT_Table_Rampart(index.toString());
	        spawns.Initialize();
	        RampartArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(Rampart index) is error : ",e);
        }
        RampartArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_Rampart getSpawns_Rampart(String index) {
        String str = "Rampart_" + index;
        if (RampartArray.containsKey(str)) {
            return RampartArray.get(str);
        }
        try {
            MT_Table_Rampart spawns = new MT_Table_Rampart(str);
            spawns.Initialize();
            RampartArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_Rampart(String index) is error : ",e);
        }
        RampartArray.put(str, null);
	    return null;
    }
    public enum Recharge1 {
        Recharge1_360,
        Recharge1_appstore,
        Recharge1_appstoretw,
        Recharge1_GooglePlay,
    }
    private HashMap<String,MT_Table_Recharge1> Recharge1Array = new HashMap<String,MT_Table_Recharge1>();
    public MT_Table_Recharge1 getSpawns(Recharge1 index) {
        if (Recharge1Array.containsKey(index.toString())) {
			MT_Table_Recharge1 spawns = Recharge1Array.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(Recharge1 index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_Recharge1 spawns = new MT_Table_Recharge1(index.toString());
	        spawns.Initialize();
	        Recharge1Array.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(Recharge1 index) is error : ",e);
        }
        Recharge1Array.put(index.toString(), null);
	    return null;
    }
    public MT_Table_Recharge1 getSpawns_Recharge1(String index) {
        String str = "Recharge1_" + index;
        if (Recharge1Array.containsKey(str)) {
            return Recharge1Array.get(str);
        }
        try {
            MT_Table_Recharge1 spawns = new MT_Table_Recharge1(str);
            spawns.Initialize();
            Recharge1Array.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_Recharge1(String index) is error : ",e);
        }
        Recharge1Array.put(str, null);
	    return null;
    }
    public enum Spawn {
        Spawn_1,
        Spawn_100,
        Spawn_10000,
        Spawn_100000,
        Spawn_100001,
        Spawn_100002,
        Spawn_100003,
        Spawn_100004,
        Spawn_100005,
        Spawn_100006,
        Spawn_100007,
        Spawn_100008,
        Spawn_100009,
        Spawn_10001,
        Spawn_10002,
        Spawn_10003,
        Spawn_10004,
        Spawn_10005,
        Spawn_10006,
        Spawn_10007,
        Spawn_10008,
        Spawn_10009,
        Spawn_101,
        Spawn_102,
        Spawn_103,
        Spawn_104,
        Spawn_105,
        Spawn_106,
        Spawn_107,
        Spawn_108,
        Spawn_109,
        Spawn_2,
        Spawn_20000,
        Spawn_20001,
        Spawn_20002,
        Spawn_20003,
        Spawn_20004,
        Spawn_20005,
        Spawn_20006,
        Spawn_20007,
        Spawn_20008,
        Spawn_20009,
        Spawn_30000,
        Spawn_30001,
        Spawn_30002,
        Spawn_30003,
        Spawn_30004,
        Spawn_30005,
        Spawn_30006,
        Spawn_30007,
        Spawn_30008,
        Spawn_30009,
        Spawn_40000,
        Spawn_40001,
        Spawn_40002,
        Spawn_40003,
        Spawn_40004,
        Spawn_40005,
        Spawn_40006,
        Spawn_40007,
        Spawn_40008,
        Spawn_40009,
        Spawn_50000,
        Spawn_50001,
        Spawn_50002,
        Spawn_50003,
        Spawn_50004,
        Spawn_50005,
        Spawn_50006,
        Spawn_50007,
        Spawn_50008,
        Spawn_50009,
        Spawn_60000,
        Spawn_60001,
        Spawn_60002,
        Spawn_60003,
        Spawn_60004,
        Spawn_60005,
        Spawn_60006,
        Spawn_60007,
        Spawn_60008,
        Spawn_60009,
        Spawn_70000,
        Spawn_70001,
        Spawn_70002,
        Spawn_70003,
        Spawn_70004,
        Spawn_70005,
        Spawn_70006,
        Spawn_70007,
        Spawn_70008,
        Spawn_70009,
        Spawn_80000,
        Spawn_80001,
        Spawn_80002,
        Spawn_80003,
        Spawn_80004,
        Spawn_80005,
        Spawn_80006,
        Spawn_80007,
        Spawn_80008,
        Spawn_80009,
        Spawn_90000,
        Spawn_90001,
        Spawn_90002,
        Spawn_90003,
        Spawn_90004,
        Spawn_90005,
        Spawn_90006,
        Spawn_90007,
        Spawn_90008,
        Spawn_90009,
    }
    private HashMap<String,MT_Table_Spawn> SpawnArray = new HashMap<String,MT_Table_Spawn>();
    public MT_Table_Spawn getSpawns(Spawn index) {
        if (SpawnArray.containsKey(index.toString())) {
			MT_Table_Spawn spawns = SpawnArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(Spawn index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_Spawn spawns = new MT_Table_Spawn(index.toString());
	        spawns.Initialize();
	        SpawnArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(Spawn index) is error : ",e);
        }
        SpawnArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_Spawn getSpawns_Spawn(String index) {
        String str = "Spawn_" + index;
        if (SpawnArray.containsKey(str)) {
            return SpawnArray.get(str);
        }
        try {
            MT_Table_Spawn spawns = new MT_Table_Spawn(str);
            spawns.Initialize();
            SpawnArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_Spawn(String index) is error : ",e);
        }
        SpawnArray.put(str, null);
	    return null;
    }
    public enum tech {
        tech_BattleLab,
    }
    private HashMap<String,MT_Table_tech> techArray = new HashMap<String,MT_Table_tech>();
    public MT_Table_tech getSpawns(tech index) {
        if (techArray.containsKey(index.toString())) {
			MT_Table_tech spawns = techArray.get(index.toString());
			if (m_ModifiedTableMap.containsKey(index.toString())){
				try {
					m_ModifiedTableMap.remove(index.toString());
					spawns.Initialize();
				} catch (Exception e) {
					TableUtil.Error("getSpawns(tech index) is error : ",e);
				}
			}
		    return spawns;
	    }
        try {
            MT_Table_tech spawns = new MT_Table_tech(index.toString());
	        spawns.Initialize();
	        techArray.put(index.toString(), spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns(tech index) is error : ",e);
        }
        techArray.put(index.toString(), null);
	    return null;
    }
    public MT_Table_tech getSpawns_tech(String index) {
        String str = "tech_" + index;
        if (techArray.containsKey(str)) {
            return techArray.get(str);
        }
        try {
            MT_Table_tech spawns = new MT_Table_tech(str);
            spawns.Initialize();
            techArray.put(str, spawns);
            return spawns;
        } catch (Exception e) {
            TableUtil.Error("getSpawns_tech(String index) is error : ",e);
        }
        techArray.put(str, null);
	    return null;
    }
    public MT_TableBase getSpawns(String key, String index) {
        if (key.equals("AirBase")) return getSpawns_AirBase(index);
        if (key.equals("assets")) return getSpawns_assets(index);
        if (key.equals("depot")) return getSpawns_depot(index);
        if (key.equals("EquipB")) return getSpawns_EquipB(index);
        if (key.equals("factory")) return getSpawns_factory(index);
        if (key.equals("flag")) return getSpawns_flag(index);
        if (key.equals("institute")) return getSpawns_institute(index);
        if (key.equals("Language")) return getSpawns_Language(index);
        if (key.equals("LegionBuild")) return getSpawns_LegionBuild(index);
        if (key.equals("main")) return getSpawns_main(index);
        if (key.equals("MedalData")) return getSpawns_MedalData(index);
        if (key.equals("Queue")) return getSpawns_Queue(index);
        if (key.equals("Rampart")) return getSpawns_Rampart(index);
        if (key.equals("Recharge1")) return getSpawns_Recharge1(index);
        if (key.equals("Spawn")) return getSpawns_Spawn(index);
        if (key.equals("tech")) return getSpawns_tech(index);
        return null;
    }
}