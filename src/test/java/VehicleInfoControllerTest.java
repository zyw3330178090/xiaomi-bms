import com.zyw.entity.Result;
import com.zyw.entity.VehicleInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class VehicleInfoControllerTest {

    private static final String BASE_URL = "http://localhost:8080/api/vehicle-info";
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testAddVehicles() {
        // 准备测试数据
        List<VehicleInfo> vehicles = Arrays.asList(
                new VehicleInfo("001", 10001L, "三元电池", 15000.5, 95.0),
                new VehicleInfo("002", 10002L, "铁锂电池", 8000.0, 98.5)
        );

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 创建请求实体
        HttpEntity<List<VehicleInfo>> request = new HttpEntity<>(vehicles, headers);

        // 发送POST请求
        ResponseEntity<Result> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                request,
                Result.class
        );

        // 验证响应
        System.out.println("添加车辆响应: " + response.getBody());
        assert response.getStatusCode() == HttpStatus.OK;
    }

    @Test
    public void testGetByVid() {
        String vid = "583e1d260fd54b77";

        // 发送GET请求
        ResponseEntity<Result> response = restTemplate.exchange(
                BASE_URL + "/" + vid,
                HttpMethod.GET,
                null,
                Result.class
        );

        // 验证响应
        System.out.println("根据VID查询响应: " + response.getBody());
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getData() != null;
    }

    @Test
    public void testGetByVin() {
        long vin = 10001L;

        // 发送GET请求
        ResponseEntity<Result> response = restTemplate.exchange(
                BASE_URL + "/by-vin/" + vin,
                HttpMethod.GET,
                null,
                Result.class
        );

        // 验证响应
        System.out.println("根据VIN查询响应: " + response.getBody());
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getData() != null;
    }

    @Test
    public void testDeleteVehicle() {
        long vin = 10001L;

        // 发送DELETE请求
        ResponseEntity<Result> response = restTemplate.exchange(
                BASE_URL + "/" + vin,
                HttpMethod.DELETE,
                null,
                Result.class
        );

        // 验证响应
        System.out.println("删除车辆响应: " + response.getBody());
        assert response.getStatusCode() == HttpStatus.OK;
    }


}