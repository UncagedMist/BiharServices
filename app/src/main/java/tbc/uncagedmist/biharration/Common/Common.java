package tbc.uncagedmist.biharration.Common;

import android.content.Context;
import android.content.Intent;

public class Common {

    //Ration Card URLs
    public static final String CHECK_STATUS_URL = "http://164.100.130.239/RCIssueSystem/AwedanStatus.aspx";
    public static final String CHECK_ALLOT_URL = "http://epos.bihar.gov.in/SRC_Trans_Int.jsp";
    public static final String DEALER_URL = "http://sfc.bihar.gov.in/downloadEChallan.htm";
    public static final String OFFICIAL_URL = "http://epds.bihar.gov.in/";
    public static final String AADHAAR_URL = "http://epos.bihar.gov.in/SRC_Trans_Int.jsp";
    public static final String DOWNLOAD_URL = "http://epds.bihar.gov.in/DistrictWiseRationCardDetailsBH.aspx";
    public static final String SEARCH_URL = "http://epds.bihar.gov.in/SearchByRCID.aspx";

    //Voter id URLs
    public static final String VOTER_REPRINT = "https://voterportal.eci.gov.in/";
    public static final String TRACK_VOTER = "https://www.nvsp.in/Forms/trackstatus";
    public static final String VOTER_SERVICES = "https://www.nvsp.in/";
    public static final String SEARCH_VOTER = "https://electoralsearch.in/";
    public static final String APPLY_VOTER = "https://www.nvsp.in/Account/Login";
    public static final String DOWNLOAD_VOTER = "https://www.nvsp.in/Home/DownloadPdf";
    public static final String EDIT_VOTER = "https://www.nvsp.in/Account/Login";

    public static final String SEARCH_AWAS = "https://pmaymis.gov.in/Open/Find_Beneficiary_Details.aspx";
    public static final String BENE_AWAS = "https://rhreporting.nic.in/netiay/AdvanceSearch.aspx";
    public static final String LIST_AWAS = "https://rhreporting.nic.in/netiay/SocialAuditReport/BeneficiaryDetailForSocialAuditReport.aspx";

    public static final String WIN_URL = "https://894.win.qureka.com";

    public static final String PRIVACY_URL = "https://docs.google.com/document/d/1h1ePmrfhM7mXCe_ctRi2w7IXRmrpWL6S1LYJ8-YpOfA/edit?usp=sharing";

    public static String CurrentURL;

    public static void shareApp(Context context)    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = "Never Miss A Thing About Ration Card. Install Bihar Ration Card App and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.biharration";
        intent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(intent, "Share Bihar Ration Card App Using"));
    }
}
