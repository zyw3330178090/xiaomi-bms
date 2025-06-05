import com.zyw.entity.Result;
import com.zyw.entity.WarningRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class BatterySignalTest {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testSubmitSignal() {
        // 1. 创建RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();

        // 2. 设置API端点URL
        String apiUrl = "http://localhost:8080/api/warn";

        // 3. 准备请求数据
        List<WarningRequest> warnDataList = Arrays.asList(
                new WarningRequest(1L, 1, "{\"Mx\":12.0,\"Mi\":0.6}"),
                new WarningRequest(2L, 2, "{\"Ix\":12.0,\"Ii\":11.7}"),
                new WarningRequest(3L, null, "{\"Mx\":11.0,\"Mi\":9.6,\"Ix\":12.0,\"Ii\":11.7}"),
                new WarningRequest(2L, 1, "{\"Ix\":12.0,\"Ii\":11.7}"),
                new WarningRequest(2L, 1, "{\"Mx\":12.0,\"Mi\":11.7}"),
                new WarningRequest(1L, 2, "{\"Mx\":12.0,\"Ix\":12.0,\"Ii\":0.6}"),
                new WarningRequest(4L, 1, "{\"Mx\":10.5,\"Mi\":8.2}"),
                new WarningRequest(2L, 3, "{\"Ix\":11.8,\"Ii\":10.9}")
        );

        // 4. 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 5. 创建请求实体
        HttpEntity<List<WarningRequest>> request = new HttpEntity<>(warnDataList, headers);
        System.out.println(request);

        // 6. 发送POST请求
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            // 7. 处理响应
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            assert response.getStatusCode()==HttpStatus.OK;
        } catch (Exception e) {
            System.err.println("Error sending request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBatterySignalByVin() {
        Long vin = 3L;

        // 发送GET请求
        ResponseEntity<Result> response = restTemplate.exchange(
                "http://localhost:8080/api" + "/" + vin,
                HttpMethod.GET,
                null,
                Result.class
        );

        // 验证响应
        System.out.println("根据VIN查询电池信号响应: " + response.getBody());
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getData() != null;
    }

}