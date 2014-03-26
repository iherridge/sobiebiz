package biz.sobie.web.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.Service;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.Store;
import biz.sobie.web.store.StoreContentPage;
import biz.sobie.web.userdashboard.inventory.MerchantDetails;
import biz.sobie.web.userdashboard.inventory.ProductHistory;

public class RowMappers {
	
	@SuppressWarnings("rawtypes")
	public static final class StoreContentPageMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			StoreContentPage storeContentPage = new StoreContentPage();
			storeContentPage.setPageId(rs.getString("PAGE_ID"));
			storeContentPage.setPageName(rs.getString("PAGE_NAME"));
			storeContentPage.setPageTittle(rs.getString("PAGE_TITTLE"));
			storeContentPage.setPageParentId(rs.getString("PAGE_PARENT_ID"));
			storeContentPage.setPageEnabled(rs.getBoolean("PAGE_ENABLED"));
			storeContentPage.setPageContent(rs.getString("PAGE_CONTENT"));
			storeContentPage.setStoreNo(rs.getString("STORE_NO"));
			return storeContentPage;
		}
	}

	@SuppressWarnings("rawtypes")
	public static final class StoreSettingsMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Store store = new Store();
			store.setStoreNo(rs.getString("STORE_NO"));
			store.setStoreAccNo(rs.getString("ACC_NO"));
			store.setStoreName(rs.getString("STORE_NAME"));
			store.setStoreDesc(rs.getString("STORE_DESC"));
			store.setStoreTel(rs.getString("STORE_TEL"));
			store.setStoreFax(rs.getString("STORE_FAX"));
			store.setStoreAdminEmail(rs.getString("STORE_ADMIN_EMAIL"));
			store.setStoreSalesDepEmail(rs.getString("STORE_SALES_DEP_EMAIL"));
			store.setStoreDepTel(rs.getString("STORE_DEP_TEL"));
			store.setStoreCustServEmail(rs.getString("STORE_CUST_SERV_EMAIL"));
			store.setStoreCustServTel(rs.getString("STORE_CUST_SERV_TEL"));
			store.setStoreCustCommentStatus(rs.getString("STORE_CUST_COMMENT_STATUS"));
			store.setStoreIncludeTaxInProductPrice(rs.getBoolean("STORE_INC_TAX_IN_PROD_PRICE"));
			store.setStoreEnableAddressValidation(rs.getBoolean("STORE_ENABLE_ADDR_VALID"));
			store.setStoreEnableFriendlyUrls(rs.getBoolean("STORE_ENABLE_FRIENDLY_URLS"));
			store.setStoreFacebookUrl(rs.getString("STORE_FACEBOOK_URL"));
			store.setStoreTwitterUrl(rs.getString("STORE_TWITTER_URL"));
			store.setStoreSkypeName(rs.getString("STORE_SKYPE_NAME"));
			store.setStoreGoogleUrl(rs.getString("STORE_GOOGLE_URL"));
			store.setStoreStumbleUponUrl(rs.getString("STORE_STUMBLE_UPON_URL"));
			store.setStoreAboutUs(rs.getString("STORE_ABOUT_US"));
			store.setLatitude(rs.getDouble("STORE_LATITUDE"));
			store.setLongitude(rs.getDouble("STORE_LONGITUDE"));
			store.setCountry(rs.getString("STORE_COUNTRY"));
			store.setCityTown(rs.getString("STORE_CITY_TOWN"));
			store.setStreet(rs.getString("STORE_STREET"));
			store.setLocationEnabled(rs.getBoolean("STORE_LOCATION_ENABLED"));
			return store;
		}
	}

	@SuppressWarnings("rawtypes")
	public static final class ImageMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			SobieImage image = new SobieImage();
			image.setImgId(rs.getString("IMG_ID"));
			image.setImage(rs.getBlob("IMG_FILE"));
			return image;
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public static final class StoreMainLayoutMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Store store = new Store();
			store.setStoreNo(rs.getString("STORE_NO"));
			store.setStoreAccNo(rs.getString("ACC_NO"));
			store.setStoreName(rs.getString("STORE_NAME"));
			store.setStoreDesc(rs.getString("STORE_DESC"));
			store.setStoreTel(rs.getString("STORE_TEL"));
			store.setStoreFax(rs.getString("STORE_FAX"));
			store.setStoreAdminEmail(rs.getString("STORE_ADMIN_EMAIL"));
			store.setStoreSalesDepEmail(rs.getString("STORE_SALES_DEP_EMAIL"));
			store.setStoreDepTel(rs.getString("STORE_DEP_TEL"));
			store.setStoreCustServEmail(rs.getString("STORE_CUST_SERV_EMAIL"));
			store.setStoreCustServTel(rs.getString("STORE_CUST_SERV_TEL"));
			store.setStoreCustCommentStatus(rs.getString("STORE_CUST_COMMENT_STATUS"));
			store.setStoreIncludeTaxInProductPrice(rs.getBoolean("STORE_INC_TAX_IN_PROD_PRICE"));
			store.setStoreEnableAddressValidation(rs.getBoolean("STORE_ENABLE_ADDR_VALID"));
			store.setStoreEnableFriendlyUrls(rs.getBoolean("STORE_ENABLE_FRIENDLY_URLS"));
			store.setStoreFacebookUrl(rs.getString("STORE_FACEBOOK_URL"));
			store.setStoreTwitterUrl(rs.getString("STORE_TWITTER_URL"));
			store.setStoreSkypeName(rs.getString("STORE_SKYPE_NAME"));
			store.setStoreGoogleUrl(rs.getString("STORE_GOOGLE_URL"));
			store.setStoreStumbleUponUrl(rs.getString("STORE_STUMBLE_UPON_URL"));
			store.setStoreAboutUs(rs.getString("STORE_ABOUT_US"));
			return store;
		}
	}

	@SuppressWarnings("rawtypes")
	public static final class SobieImageMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			SobieImage sobieImage = new SobieImage();
			sobieImage.setImgId(rs.getString("IMG_ID"));
			sobieImage.setImgFilename(rs.getString("IMG_FILENAME"));
			sobieImage.setImgType(rs.getString("IMG_TYPE"));
			sobieImage.setImgNotes(rs.getString("IMG_NOTES"));
			sobieImage.setImage(rs.getBlob("IMG_FILE"));
			sobieImage.setImageAlbum(rs.getString("IMG_ALBUM"));
			sobieImage.setPrimaryImage(rs.getString("PRIMARY_IMG"));
			sobieImage.setProdId(rs.getString("PROD_ID"));
			sobieImage.setServId(rs.getString("SERV_ID"));
			return sobieImage;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static final class ProductMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product p = new Product();
			p.setProdId(rs.getString("PROD_ID"));
			p.setProdName(rs.getString("PROD_NAME"));
			p.setProdSkuPartNo(rs.getString("PROD_SKU_PART_NO"));
			p.setProdCategory(rs.getString("PROD_CATEGORY"));
			p.setProdEnabled(rs.getBoolean("PROD_ENABLED"));
			p.setProdRetailPrice(rs.getDouble("PROD_RETAIL_PRICE"));
			p.setProdSalePrice(rs.getDouble("PROD_SALE_PRICE"));
			p.setProdWholesalePrice(rs.getDouble("PROD_WHOLESALE_PRICE"));
			p.setTaxClassId(rs.getString("TAX_CLASS_ID"));
			p.setProdAddToCart(rs.getBoolean("PROD_ADD_TO_CART"));
			p.setProdQtyImedAvail(rs.getInt("PROD_QTY_IMED_AVAIL"));
			p.setProdMinPurchaseQty(rs.getInt("PROD_MIN_PURCHASE_QTY"));
			p.setProdMaxPurchaseQty(rs.getInt("PROD_MAX_PURCHASE_QTY"));
			p.setProdFreeShipping(rs.getBoolean("PROD_FREE_SHIPPING"));
			p.setProdShipSeperate(rs.getBoolean("PROD_SHIP_SEPERATE"));
			p.setProdShippingTypeId(rs.getString("PROD_SHIPPING_TYPE_ID"));
			p.setProdWeight(rs.getDouble("PROD_WEIGHT"));
			p.setProdHeight(rs.getDouble("PROD_HEIGHT"));
			p.setProdWidth(rs.getDouble("PROD_WIDTH"));
			p.setProdLenght(rs.getDouble("PROD_LENGHT"));
			p.setProdShippingEnabled(rs.getBoolean("PROD_SHIPPING_ENABLED"));
			p.setProdDesc(rs.getString("PROD_DESC"));
			p.setProdFeatures(rs.getString("PROD_FEATURES"));
			p.setProdSpecification(rs.getString("PROD_SPECIFICATION"));
			p.setProdShippingInfo(rs.getString("PROD_SHIPPING_INFO"));
			p.setProdYouTubeUrl(rs.getString("PROD_YOUTUBE_URL"));
			p.setProdCommentsEnabled(rs.getBoolean("PROD_COMMENTS_ENABLED"));
			p.setProdQnaEnabled(rs.getBoolean("PROD_QNA_ENABLED"));
			p.setProdBrand(rs.getString("PROD_BRAND"));
			
			p.setOwnerProdId(rs.getString("OWNER_PROD_ID"));
			
			p.setProdStatus(rs.getString("PROD_STATUS"));
			p.setOwnerAccNo(rs.getString("OWNER_ACC_NO")); //Owner Store Number
			
			p.setSellerStoreNo(rs.getString("STORE_NO"));
			p.setProdFeatured(rs.getBoolean("PROD_FEATURED"));
			p.setProdLikes(rs.getInt("OWNER_PROD_ID_COUNT"));
			return p;
		}
	}
	
	public static final class ProductHistoryMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductHistory productHistory = new ProductHistory();
			productHistory.setProdId(rs.getString("PROD_ID"));
			productHistory.setDateTime(rs.getDate("DATETIME"));
			productHistory.setFieldName(rs.getString("FIELD_NAME"));
			productHistory.setFieldValueFrom(rs.getString("FIELD_VALUE_FROM"));
			productHistory.setFieldValueTo(rs.getString("FIELD_VALUE_TO"));
			return productHistory;
		}
	}
	@SuppressWarnings("rawtypes")
	public static final class ShoppingCartListMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product p = new Product();
			p.setProdId(rs.getString("PROD_ID"));
			p.setProdName(rs.getString("PROD_NAME"));
			p.setProdSkuPartNo(rs.getString("PROD_SKU_PART_NO"));
			p.setProdCategory(rs.getString("PROD_CATEGORY"));
			p.setProdEnabled(rs.getBoolean("PROD_ENABLED"));
			p.setProdRetailPrice(rs.getDouble("PROD_RETAIL_PRICE"));
			p.setProdSalePrice(rs.getDouble("PROD_SALE_PRICE"));
			p.setProdWholesalePrice(rs.getDouble("PROD_WHOLESALE_PRICE"));
			p.setTaxClassId(rs.getString("TAX_CLASS_ID"));
			p.setProdAddToCart(rs.getBoolean("PROD_ADD_TO_CART"));
			p.setProdQtyImedAvail(rs.getInt("PROD_QTY_IMED_AVAIL"));
			p.setProdMinPurchaseQty(rs.getInt("PROD_MIN_PURCHASE_QTY"));
			p.setProdMaxPurchaseQty(rs.getInt("PROD_MAX_PURCHASE_QTY"));
			p.setProdFreeShipping(rs.getBoolean("PROD_FREE_SHIPPING"));
			p.setProdShipSeperate(rs.getBoolean("PROD_SHIP_SEPERATE"));
			p.setProdShippingTypeId(rs.getString("PROD_SHIPPING_TYPE_ID"));
			p.setProdWeight(rs.getDouble("PROD_WEIGHT"));
			p.setProdHeight(rs.getDouble("PROD_HEIGHT"));
			p.setProdWidth(rs.getDouble("PROD_WIDTH"));
			p.setProdLenght(rs.getDouble("PROD_LENGHT"));
			p.setProdShippingEnabled(rs.getBoolean("PROD_SHIPPING_ENABLED"));
			p.setProdDesc(rs.getString("PROD_DESC"));
			p.setProdFeatures(rs.getString("PROD_FEATURES"));
			p.setProdSpecification(rs.getString("PROD_SPECIFICATION"));
			p.setProdShippingInfo(rs.getString("PROD_SHIPPING_INFO"));
			p.setProdYouTubeUrl(rs.getString("PROD_YOUTUBE_URL"));
			p.setProdCommentsEnabled(rs.getBoolean("PROD_COMMENTS_ENABLED"));
			p.setProdQnaEnabled(rs.getBoolean("PROD_QNA_ENABLED"));
			p.setProdBrand(rs.getString("PROD_BRAND"));
			
			p.setSellerStoreNo(rs.getString("SELLER_STORE_NO"));
			p.setOwnerStoreNo(rs.getString("OWNER_STORE_NO"));
			p.setSellerAccNo(rs.getString("SELLER_ACC_NO"));
			p.setOwnerAccNo(rs.getString("OWNER_ACC_NO"));
			
			return p;
		}
	}

	@SuppressWarnings("rawtypes")
	public static final class ProductListMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product p = new Product();
			p.setProdId(rs.getString("PROD_ID"));
			p.setProdName(rs.getString("PROD_NAME"));
			p.setProdSkuPartNo(rs.getString("PROD_SKU_PART_NO"));
			p.setProdCategory(rs.getString("PROD_CATEGORY"));
			p.setProdEnabled(rs.getBoolean("PROD_ENABLED"));
			p.setProdRetailPrice(rs.getDouble("PROD_RETAIL_PRICE"));
			p.setProdSalePrice(rs.getDouble("PROD_SALE_PRICE"));
			p.setProdWholesalePrice(rs.getDouble("PROD_WHOLESALE_PRICE"));
			p.setTaxClassId(rs.getString("TAX_CLASS_ID"));
			p.setProdAddToCart(rs.getBoolean("PROD_ADD_TO_CART"));
			p.setProdQtyImedAvail(rs.getInt("PROD_QTY_IMED_AVAIL"));
			p.setProdMinPurchaseQty(rs.getInt("PROD_MIN_PURCHASE_QTY"));
			p.setProdMaxPurchaseQty(rs.getInt("PROD_MAX_PURCHASE_QTY"));
			p.setProdFreeShipping(rs.getBoolean("PROD_FREE_SHIPPING"));
			p.setProdShipSeperate(rs.getBoolean("PROD_SHIP_SEPERATE"));
			p.setProdShippingTypeId(rs.getString("PROD_SHIPPING_TYPE_ID"));
			p.setProdWeight(rs.getDouble("PROD_WEIGHT"));
			p.setProdHeight(rs.getDouble("PROD_HEIGHT"));
			p.setProdWidth(rs.getDouble("PROD_WIDTH"));
			p.setProdLenght(rs.getDouble("PROD_LENGHT"));
			p.setProdShippingEnabled(rs.getBoolean("PROD_SHIPPING_ENABLED"));
			p.setProdDesc(rs.getString("PROD_DESC"));
			p.setProdFeatures(rs.getString("PROD_FEATURES"));
			p.setProdSpecification(rs.getString("PROD_SPECIFICATION"));
			p.setProdShippingInfo(rs.getString("PROD_SHIPPING_INFO"));
			p.setProdYouTubeUrl(rs.getString("PROD_YOUTUBE_URL"));
			p.setProdCommentsEnabled(rs.getBoolean("PROD_COMMENTS_ENABLED"));
			p.setProdQnaEnabled(rs.getBoolean("PROD_QNA_ENABLED"));
			p.setProdBrand(rs.getString("PROD_BRAND"));
			
			p.setOwnerProdId(rs.getString("OWNER_PROD_ID"));
			
			p.setSellerStoreNo(rs.getString("SELLER_STORE_NO"));
			p.setOwnerStoreNo(rs.getString("OWNER_STORE_NO"));
			p.setOwnerAccNo(rs.getString("OWNED_ITEM"));
			p.setProdLikes(rs.getInt("OWNER_PROD_ID_COUNT"));
			
			SobieImage sobieImage = new SobieImage();
			sobieImage.setImgId(rs.getString("IMG_ID"));
			sobieImage.setImgFilename(rs.getString("IMG_FILENAME"));
			sobieImage.setImgType(rs.getString("IMG_TYPE"));
			sobieImage.setImgNotes(rs.getString("IMG_NOTES"));
			sobieImage.setImage(rs.getBlob("IMG_FILE"));
			sobieImage.setPrimaryImage(rs.getString("PRIMARY_IMG"));
			sobieImage.setProdId(rs.getString("PROD_ID"));
			
			ArrayList<SobieImage> productImage = new ArrayList<SobieImage>();
			productImage.add(sobieImage);
			p.setImageCatalog(productImage);
			
			return p;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static final class StoreMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Store store = new Store();
			store.setStoreName(rs.getString("STORE_NAME"));
			store.setStoreAccNo(rs.getString("ACC_NO"));
			store.setStoreNo(rs.getString("STORE_NO"));
			store.setStoreImage(rs.getBlob("IMG_FILE"));
			return store;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static final class MerchantStoreListMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			MerchantDetails merchantDetails = new MerchantDetails();
			merchantDetails.setStoreNo(rs.getString("STORE_NO"));
			merchantDetails.setAccNo(rs.getString("ACC_NO"));
			return merchantDetails;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static final class ServiceProviderListMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Service service  = new Service();
			
			service.setServId(rs.getString("SERV_ID"));
			service.setServName(rs.getString("SERV_NAME"));
			service.setServRef(rs.getString("SERV_REF"));
			service.setServCategory(rs.getString("SERV_CATEGORY"));
			service.setServEnabled(rs.getBoolean("SERV_ENABLED"));
			service.setServFeatured(rs.getBoolean("SERV_FEATURED"));
			service.setServFixedPrice(rs.getDouble("SERV_FIXED_PRICE"));
			service.setServFixedRateType(rs.getString("SERV_FIXED_RATE_TYPE"));
			service.setServFixedRateUnit(rs.getDouble("SERV_FIXED_RATE_UNIT"));
			service.setServTaxClassId(rs.getString("SERV_TAX_CLASS_ID"));
			service.setServQuatationReqEnabled(rs.getBoolean("SERV_QUATATION_REQ_ENABLED"));
			service.setServOhDaysMon(rs.getBoolean("SERV_OH_DAYS_MON"));
			service.setServOhDaysTue(rs.getBoolean("SERV_OH_DAYS_TUE"));
			service.setServOhDaysWed(rs.getBoolean("SERV_OH_DAYS_WED"));
			service.setServOhDaysThu(rs.getBoolean("SERV_OH_DAYS_THU"));
			service.setServOhDaysFri(rs.getBoolean("SERV_OH_DAYS_FRI"));
			service.setServOhDaysSat(rs.getBoolean("SERV_OH_DAYS_SAT"));
			service.setServOhDaysSun(rs.getBoolean("SERV_OH_DAYS_SUN"));
			service.setServStartTime(rs.getTime("SERV_TIME_START"));
			service.setServEndTime(rs.getTime("SERV_TIME_END"));
			service.setServYoutubeUrl(rs.getString("SERV_YOUTUBE_URL"));
			service.setServAltText(rs.getString("SERV_ALT_TEXT"));
			service.setServCommentsEnabled(rs.getBoolean("SERV_COMMENTS_ENABLED"));
			service.setServQnaEnabled(rs.getBoolean("SERV_QNA_ENABLED"));
			service.setServDesc(rs.getString("SERV_DESC"));
			service.setServFeatures(rs.getString("SERV_FEATURES"));
			service.setServSpecification(rs.getString("SERV_SPECIFICATION"));
			return service;
		}
	}
	
}