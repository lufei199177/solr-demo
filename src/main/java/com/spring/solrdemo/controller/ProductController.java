package com.spring.solrdemo.controller;

import com.spring.solrdemo.model.Product;
import com.spring.solrdemo.service.ProductSolrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.data.solr.core.query.result.StatsPage;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author lufei
 * @date 2020/12/28
 * @desc
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductSolrService productSolrService;

    @GetMapping("/get/{id}")
    public Product getById(@PathVariable("id") String id) {
        Optional<Product> optionalProduct = this.productSolrService.findById(id);
        return optionalProduct.orElse(null);
    }

    @GetMapping("/save/{id}")
    public int save(@PathVariable("id") String id) {
        return this.productSolrService.save(this.createProduct(id));
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") String id) {
        this.productSolrService.update(this.createPartialUpdate(id));
        return "update success";
    }

    @GetMapping("/batchSave")
    public String batchSave(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return "ids不能为空";
        }
        String[] array = ids.split(",");
        List<Product> list = new ArrayList<>(array.length);
        for (String id : array) {
            list.add(this.createProduct(id));
        }
        this.productSolrService.batchSave(list);
        return "保存成功";
    }

    @GetMapping("/deleteByIds")
    public String deleteByIds(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return "ids不能为空";
        }
        this.productSolrService.deleteByIds(Arrays.asList(ids.split(",")));
        return "删除成功";
    }

    @GetMapping("/listFacetPage")
    public FacetPage<Product> listFacetPage() {
        return this.productSolrService.listFacetPage();
    }

    @GetMapping("/listScoredPage")
    public ScoredPage<Product> listScoredPage() {
        return this.productSolrService.listScoredPage();
    }

    @GetMapping("/statsPage")
    public StatsPage<Product> statsPage() {
        return this.productSolrService.statsPage();
    }

    private Product createProduct(String id) {
        Product product = new Product();
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - random.nextInt(10));
        product.setId(id);
        product.setAccount(random.nextDouble() * 10000);
        product.setBoothCount(random.nextInt(100));
        product.setCanSellCount(random.nextInt(100));
        product.setConsume7DayQFB(random.nextDouble() * 100);
        product.setDial7DayCount(random.nextInt(100));
        product.setDialConsumeAvg(product.getConsume7DayQFB() / product.getDial7DayCount() + "");
        product.setExpire(random.nextInt(10) % 2 == 0);
        product.setExpiringSellCount(random.nextInt(100));
        product.setLevelName("level" + id);
        product.setName("name" + id);
        product.setOpenPermissionCount(random.nextInt(100));
        product.setOwnerCuId("ownerCuId" + id);
        product.setPhotoUrl("photoUrl" + id);
        product.setQBalance(random.nextInt(100));
        product.setRecharge(random.nextInt(10) % 2 == 0);
        product.setSell7DayCount(random.nextInt(100));
        product.setSellingCount(random.nextInt(100));
        product.setUnreadMsgCount(random.nextInt(100));
        product.setVirtualNumber("virtualNumber" + id);
        product.setDate(calendar.getTime());
        return product;
    }

    private PartialUpdate createPartialUpdate(String id) {
        PartialUpdate update = new PartialUpdate("id", id);
        Random random = new Random();
        double consume7DayQFB = random.nextDouble() * 100;
        int dial7DayCount = random.nextInt(100);
        update.add("account", random.nextDouble() * 10000);
        update.add("boothCount", random.nextInt(100));
        update.add("canSellCount", random.nextInt(100));
        update.add("consume7DayQFB", consume7DayQFB);
        update.add("dial7DayCount", dial7DayCount);
        update.add("dialConsumeAvg", consume7DayQFB / dial7DayCount + "");
        update.add("expire", random.nextInt(10) % 2 == 0);
        update.add("expiringSellCount", random.nextInt(100));
        update.add("levelName", "level" + id);
        update.add("name", "name" + id);
        update.add("openPermissionCount", random.nextInt(100));
        update.add("ownerCuId", "ownerCuId" + id);
        update.add("photoUrl", "photoUrl" + id);
        update.add("qBalance", random.nextInt(100));
        update.add("recharge", random.nextInt(10) % 2 == 0);
        update.add("sell7DayCount", random.nextInt(100));
        update.add("sellingCount", random.nextInt(100));
        update.add("unreadMsgCount", random.nextInt(100));
        update.add("virtualNumber", "virtualNumber" + id);
        return update;
    }
}
