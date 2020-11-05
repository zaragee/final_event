
package pizza.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

//@FeignClient(name="delivery", url="http://delivery:8080")
@FeignClient(name="delivery", url="http://localhost:8083")
public interface DeliveryService {

    @RequestMapping(method= RequestMethod.POST, path="/deliveries")
    public void addGift(@RequestBody Delivery delivery);

}