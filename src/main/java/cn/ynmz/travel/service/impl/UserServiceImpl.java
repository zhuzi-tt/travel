package cn.ynmz.travel.service.impl;

import cn.ynmz.travel.dao.UserDao;
import cn.ynmz.travel.dao.impl.UserDaoImpl;
import cn.ynmz.travel.domain.User;
import cn.ynmz.travel.service.UserService;
import cn.ynmz.travel.util.MailUtils;
import cn.ynmz.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
   private UserDao dao = new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        User use1 = dao.findByUsernameuser(user.getUsername());
        if (use1 != null) {
            return false;
        } else {
            //设置激活码
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            dao.save(user);

            //发送邮件
            String content = "       <h3><span>旅游须知</span></h3>\n" +
                    "                <p>1、旅行社已投保旅行社责任险。建议游客购买旅游意外保险 <br>\n" +
                    "                <p>2、旅游者参加打猎、潜水、海边游泳、漂流、滑水、滑雪、滑草、蹦极、跳伞、滑翔、乘热气球、骑马、赛车、攀岩、水疗、水上飞机等属于高风险性游乐项目的，敬请旅游者务必在参加前充分了解项目的安全须知并确保身体状况能适应此类活动；如旅游者不具备较好的身体条件及技能，可能会造成身体伤害。</p>\n" +
                    "                <p>3、参加出海活动时，请务必穿着救生设备。参加水上活动应注意自己的身体状况，有心脏病、冠心病、高血压、感冒、发烧和饮酒及餐后不可以参加水上活动及潜水。在海里活动时，严禁触摸海洋中各种鱼类，水母，海胆，珊瑚等海洋生物，避免被其蛰伤。老人和小孩必须有成年人陪同才能参加合适的水上活动。在海边游玩时，注意保管好随身携带的贵重物品。</p>\n" +
                    "                <p>4、根据中国旅游总署的规定，旅客在购买的物品，注意价格，小心财务。详细内容见《中华人民共和国海关总署公告2010年第54号文件》。</p>\n" +
                    "                <p>5、建议出发时行李托运，贵重物品、常用物品、常用药品、御寒衣物等请随身携带，尽量不要托运。行李延误属于不可抗力因素，我司将全力协助客人跟进后续工作，但我司对此不承担任何责任。</p>\n" +
                    "                <p>尊敬的游客朋友，收到邮件后，请在72小时内及时激活，以免失效，给您带来的不便请您谅解</p>\n" +
                    "                <a href='http://192.168.162.133/user/active?code="+user.getCode()+"'>点击激活【天选旅游网】</a>";
            //调用激活邮件发送工具类
            MailUtils.sendMail(user.getEmail(), content, "天选旅游，激活邮件");

            return true;

        }
    }

    @Override
    public boolean active(String code) {
        //根据激活码查询用户
        User user = dao.findByCode(code);
        if (user != null) {
            //修改激活状态
            dao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User user) {
       User userlogin= dao.login(user);
        return userlogin;
    }
}
