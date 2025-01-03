package com.sparta.b2c.product;

import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

class ProductCsvFileGenerationTest {

    private static final String CSV_FILE_PATH = "products.csv";

    // 사용을 원할시 Disabled 어노테이션을 삭제할 것
    @Disabled("더미 데이터 100만건 csv파일 생성 테스트코드")
    @Test
    public void generateCsvTest() throws IOException {
        int numberOfRecords = 1000000;

        FileWriter writer = new FileWriter(CSV_FILE_PATH);

        writer.append("id,name,description,price,status,category,sub_category,member_id,created_at,modified_at\n");

        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();

        for (int i = 1; i <= numberOfRecords; i++) {
            String id = String.valueOf(i);
            char randomChar1 = (char) ('a' + random.nextInt(26));
            char randomChar2 = (char) ('a' + random.nextInt(26));
            String name = "상품" + randomChar1 + randomChar2;
            String description = "상품 설명";
            int price = random.nextInt(999901) + 100; // 100 ~ 1000000 사이의 랜덤 가격

            String status = getWeightedStatus(random);

            Category[] categories = Category.values();
            Category category = categories[random.nextInt(categories.length)];
            Category.SubCategory[] subCategories = category.getSubCategories();
            Category.SubCategory subCategory = subCategories[random.nextInt(subCategories.length)];

            long randomTime = oneYearAgo.getTime() + (long) (random.nextDouble() * (new Date().getTime() - oneYearAgo.getTime()));
            String randomDate = dateFormat.format(new Date(randomTime));

            String memberId = "1";
            String createdAt = randomDate;
            String modifiedAt = randomDate;

            writer.append(id).append(",")
                    .append(name).append(",")
                    .append(description).append(",")
                    .append(String.valueOf(price)).append(",")
                    .append(status).append(",")
                    .append(category.name()).append(",")
                    .append(subCategory.name()).append(",")
                    .append(memberId).append(",")
                    .append(createdAt).append(",")
                    .append(modifiedAt).append("\n");
        }

        writer.flush();
        writer.close();
    }

    private String getWeightedStatus(Random random) {
        int weightedRandom = random.nextInt(100); // 0~99
        if (weightedRandom < 70) {
            return "ON_SALE"; // 70% 확률
        } else if (weightedRandom < 90) {
            return "PENDING"; // 20% 확률 (70~89)
        } else {
            return "OFF_SALE"; // 10% 확률 (90~99)
        }
    }
}