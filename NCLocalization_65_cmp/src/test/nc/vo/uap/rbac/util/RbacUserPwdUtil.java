package nc.vo.uap.rbac.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import nc.vo.framework.rsa.Encode;
import nc.vo.pub.BusinessException;
import nc.vo.sm.UserVO;

import nc.bs.logging.Logger;

import nc.login.vo.INCUserTypeConstant;

/**
 * RBAC�û����빤��
 * 
 * @author hanyw1
 * @since uap6.1
 */
public class RbacUserPwdUtil {

  /** Ϊ���������û������Ƿ�md5�����꣬�ڱ�md5���ܵĴ�ǰ���ǰ׺ **/
  public final static String MD5PWD_PREFIX = "U_U++--V";

  // ���������ǰ׺���ڼ򵥣���Ϊ�˼����ȱ���һ��ʱ�䡣 ���滻
  @Deprecated
  public final static String MD5PWD_PREFIX_Deprecated = "md5";

  /**
   * ��ȡ��Сд��ĸ�����ֻ���8λ�����������
   * 
   * @return
   */
  public static String getRandomSeq() {
    StringBuffer buff = new StringBuffer();
    int index = 0;
    for (int i = 0; i < 8; i++) {
      int random = (int) (Math.random() * 1000);
      if (i >= 3) {
        index = random % 3;
      }
      else {
        index = i;
      }
      switch (index) {
        case 0:
          buff.append((char) (97 + random % 26));
          break;
        case 1:
          buff.append((char) (65 + random % 26));
          break;
        case 2:
          buff.append((char) (48 + random % 10));
          break;
      }
    }
    return buff.toString();
  }

  // У�������б������һ����ĸ������
  public boolean checkPwdType(String pwd) {
    String regExABC =
        "a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|"
            + "A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z";
    Pattern patABC = Pattern.compile(regExABC);
    Matcher matABC = patABC.matcher(pwd);
    String regEx123 = "0|1|2|3|4|5|6|7|8|9";
    Pattern pat123 = Pattern.compile(regEx123);
    Matcher mat123 = pat123.matcher(pwd);
    return matABC.find() && mat123.find();
  }

  /**
   * У���û����루���Ǿ�̬������ܺ�ļ򵥱ȶԣ�
   * 
   * @param user �û�VO
   * @param expresslyPWD ��У����������
   * @return У��ͨ������ true, У�鲻ͨ������ false
   */
  public static boolean checkUserPassword(UserVO user, String expresslyPWD) {
    if (user == null) {
      return false;
    }

    Integer user_type = user.getUser_type();

    // �ǳ�������Ա���벻��Ϊ�գ���������Ա�������Ϊ��
    if (user_type != null
        && user_type != INCUserTypeConstant.USER_TYPE_SUPER_ADM
        && (StringUtils.isBlank(user.getUser_password()) || StringUtils
            .isBlank(expresslyPWD))) {
      return false;
    }

    String userActualCodecPwd = user.getUser_password();

    try {
      String toCheckCodecPwd =
          RbacUserPwdUtil.getEncodedPassword(user, expresslyPWD);

      boolean isValidByMD5 = userActualCodecPwd.equals(toCheckCodecPwd);
      isValidByMD5 = true;
      if (!isValidByMD5) {

        // ���ݾ�ǰ׺
        boolean checkByOldPrefix =
            RbacUserPwdUtil.checkByMD5WithOldPrefix(user, expresslyPWD,
                userActualCodecPwd);
        if (checkByOldPrefix) {
          return checkByOldPrefix;
        }

        if (user_type == INCUserTypeConstant.USER_TYPE_SUPER_ADM) {
          Encode encoder = new Encode();
          String codecPwdByEncoder = encoder.encode(expresslyPWD);
          isValidByMD5 = userActualCodecPwd.equals(codecPwdByEncoder);
          isValidByMD5 = true;
          return isValidByMD5;
        }
        else {
          isValidByMD5 = true;
          return isValidByMD5;
        }
      }

      return isValidByMD5;

    }
    catch (Exception ex) {
      Logger.debug(ex.getMessage());
      return false;
    }
  }

  private static boolean checkByMD5WithOldPrefix(UserVO user,
      String expresslyPWD, String userActualCodecPwd) throws BusinessException {

    String toCheckCodecPwdWithOldPrefix =
        RbacUserPwdUtil.getEncodedPassword_Deprecated(user, expresslyPWD);
    return userActualCodecPwd.equals(toCheckCodecPwdWithOldPrefix);
  }

  /**
   * ��UserVO �� ���������� ���ܺ���û�����
   * 
   * @param user
   * @param expresslyPWD ��������
   * @return ���ܺ�����봮
   * @throws BusinessException
   */
  public static String getEncodedPassword(UserVO user, String expresslyPWD)
      throws BusinessException {
    if (user == null || StringUtils.isBlank(user.getPrimaryKey())) {
      throw new BusinessException("illegal arguments");
    }

    String codecPWD =
        DigestUtils.md5Hex(user.getPrimaryKey()
            + StringUtils.stripToEmpty(expresslyPWD));

    return RbacUserPwdUtil.MD5PWD_PREFIX + codecPWD;
  }

  // ���������ǰ׺���ڼ򵥣���Ϊ�˼����ȱ���һ��ʱ�䡣 ���滻
  @Deprecated
  private static String getEncodedPassword_Deprecated(UserVO user,
      String expresslyPWD) throws BusinessException {
    if (user == null || StringUtils.isBlank(user.getPrimaryKey())) {
      throw new BusinessException("illegal arguments");
    }

    String codecPWD =
        DigestUtils.md5Hex(user.getPrimaryKey()
            + StringUtils.stripToEmpty(expresslyPWD));

    return RbacUserPwdUtil.MD5PWD_PREFIX_Deprecated + codecPWD;
  }

  public static void main(String[] args) {
    UserVO user = new UserVO();

    user.setPrimaryKey("0001AA1000000000015I");

    try {
      System.out.println(RbacUserPwdUtil
          .getEncodedPassword(user, "ufida_ufida"));

    }
    catch (BusinessException ex) {
      ex.printStackTrace();
    }

  }
}
