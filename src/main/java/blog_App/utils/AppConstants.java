package blog_App.utils;

public class AppConstants {
	public static final String PAGE_NUMBER="0";
	public static final String PAGE_SIZE="10";
	public static final String SORT_BY="postId";
	public static final String SORT_DIR="asc";
	public static final int NORMAL_ROLE=502;
	public static final int ADMIN_ROLE=501;
	public static final String[] PUBLIC_URLS= {
			"/api/auth/**",
			"/api/users/register",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**",
			"/api/upload",
			"/api/v1/email"
	};
}
