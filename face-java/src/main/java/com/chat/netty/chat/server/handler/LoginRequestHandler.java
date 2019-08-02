package com.chat.netty.chat.server.handler;

import com.chat.netty.chat.ChatInfo;
import com.chat.netty.chat.common.Constant;
import com.chat.netty.chat.server.repository.UserRepository;
import com.chat.netty.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<ChatInfo.Chat>{

    private UserRepository userRepository = new UserRepository();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatInfo.Chat msg) throws Exception {

        ChatInfo.Chat.MessageType msgType = msg.getMsgType();
        if(ChatInfo.Chat.MessageType.LOGIN_REQUEST == msgType) {
            // 是登录请求，验证用户信息是否正确
            ChatInfo.User loginUser = msg.getLoginRequest().getLoginUser();
            ChatInfo.Chat chat = null;

            ChatInfo.User userInfo = loginCheck(loginUser.getUsername(), loginUser.getPassword());
            if(userInfo != null) {
                chat = loginSuccess(userInfo);

                // 保存userId 和 channel关联关系
                SessionUtil.addChannel(userInfo.getUserId(), ctx.channel());

                System.out.println(userInfo.getUserId() + " 登录成功");

            }else {
                chat = loginFail(loginUser);
            }

            ctx.writeAndFlush(chat);

        }else {
            // 触发ChannelPipeline 中的下一个handler 处理业务
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 构建登录成功的响应
     * @param loginUser
     * @return
     */
    private ChatInfo.Chat loginSuccess(ChatInfo.User loginUser) {
        String token = UUID.randomUUID().toString().replace("-", "");
        ChatInfo.LoginResponse loginResponse = ChatInfo.LoginResponse.newBuilder()
                .setToken(token).setCode(Constant.STATUS.SUCCESS).setMsg("登录成功").setUserInfo(loginUser).build();

        ChatInfo.Chat chat = ChatInfo.Chat.newBuilder()
                .setLoginResponse(loginResponse).setMsgType(ChatInfo.Chat.MessageType.LOGIN_RESPONSE).build();

        return chat;
    }

    /**
     * 构建登录失败的响应
     * @param loginUser
     * @return
     */
    private ChatInfo.Chat loginFail(ChatInfo.User loginUser) {
        ChatInfo.LoginResponse loginResponse = ChatInfo.LoginResponse.newBuilder()
                .setUserInfo(loginUser).setCode(Constant.STATUS.LOGIN_FAIL).
                        setMsg("登录失败，帐号或密码错误").build();

        ChatInfo.Chat chat = ChatInfo.Chat.newBuilder()
                .setLoginResponse(loginResponse).setMsgType(ChatInfo.Chat.MessageType.LOGIN_RESPONSE).build();

        return chat;
    }


    /**
     * 用户登录校验
     * @param username
     * @param password
     * @return
     */
    private ChatInfo.User loginCheck(String username, String password) {
        ChatInfo.User user = userRepository.queryUser(username, password);
        return user;
    }
}
