package one.enchanter.rndevops.jester.playground.backend.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.repository.UserSessionRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionInvalidateListener implements HttpSessionListener {

    private final UserSessionRepository userSessionRepository;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // セッション作成時は特に何もしなくてOK（ログイン時にDB登録しているはず）
    }

    @Override
    @Transactional // データベースの削除処理を行うためトランザクションを付与
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        log.info("セッションが無効になりました : {}", sessionId);

        // DBから該当するセッション情報を削除
        userSessionRepository.deleteBySessionId(sessionId);
    }

}
