# gfg-productcatalog-services

# Design

The project is a versioned microservice implemented using Springboot.
ProductIdentifier is a unique key which is used to identify a product. (I have seen GFG website and found several products from different brand and hence decided to keep ProductId and Brand name together as a unique key for product catalog. It may also possible that multiple 
brands can have same productIds for their products so brand name with their id uniquely defne the product.)

### SAVE / UPDATE -- 
I keep a single endpoint for product create and update. Either you create a product or update an existing product the service (save function) takes care of it.
It provides versioned implementation that means when a user tries to store a product again it creata version of product.

## POST- localhost:8080/product/store   (save())

	{
		"productIdentifier" : 
		{	
			"productId" :"779",
			"brand": "BRAND_2"
		},
		"color": "CLOOR_1",
		"description": "DESCRIPTION_1",
		"price" : "22.10",
		"title" :"TITLE_1"
	}
	
## SAVE LIST/ UPDATE LIST -- 
Similar kind of implementation as we have in save/ update function.
The benefit is it processes a batch of products. I Considered list of products may have duplicates while saving data, accordingly it creates versions. 	
### POST - localhost:8080/product/storeList

	[
		{
			"productIdentifier" : 
			{	
				"productId" :"779",
				"brand": "BRAND_2"
			},
			"color": "CLOOR_1",
			"description": "DESCRIPTION_1",
			"price" : "22.10",
			"title" :"TITLE_1"
		},
		{
			"productIdentifier" : 
			{	
				"productId" :"778",
				"brand": "BRAND_2"
			},
			"color": "CLOOR_1",
			"description": "DESCRIPTION_1",
			"price" : "22.10",
			"title" :"TITLE_1"
		},
		{
			"productIdentifier" : 
			{	
				"productId" :"778",
				"brand": "BRAND_2"
			},
			"color": "CLOOR_1",
			"description": "DESCRIPTION_1",
			"price" : "22.10",
			"title" :"TITLE_1"
		}
	]
	
## ProdctIdentifier identifies a product and it is used to find a product.
### POST - localhost:8080/product/content
works O(1) time/space complexity.

	{	
		"productId" :"779",
		"brand": "BRAND_2"
	}
	
		
## A global search, sort and paging api is implemented to get desired search outcome. Example matcher, sort and paging are used from spring which provide ease of implementation and a generic to global search for product catalog services
### POST - localhost:8080/product/search

	{
		"productSearchFilter" : 
			{	
				"title": "TITLE_1"
			},
		"productSortFilter" : {
			"sortProperties" :["color", "BRAND"],
			"sortOrder" :"ASC",
			"pageSize" : "1",
			"pageCount" : "0"
		}
	}

## Delete api deletes the product, as well as stores the version. A product identifier is used to delete the project as it is tickets a product.
### POST - localhost:8080/product/delete
works O(1) time/space complexity.

	 {	
	    "productId" :"778",
	    "brand": "BRAND_2"
	 }

## Additional search by title and description is introduced, also search service mentioned above can be used to find desired outcome.
### POST - localhost:8080/product/searchByTitleAndDescription
works O(1) time/space complexity.

	   {	
	      "title": "TITLE_1",
	      "description":"desc"
	   }

## Authentication - 
I have used basic authentication using spring security and thus please make a note that all product sevices are available through basic 
authentication by providing username and password with authorization header.
username - GFG
password -productCatalog


## Rest end point using swagger
All rest end points are available on http://localhost:8080/swagger-ui.html , when application is up and running. 

## H2 DATABASE -
Database can be connected on http://localhost:8080/h2-console JDBC URL - jdbc:h2:mem:productcatalogdb 
User name - sa 
password No password required.
DB configuration is available in application.yml inside src/main/resources folder structure.

## API end point exception handling
RestResponseEntityExceptionHandler. java provides global controller advice in order to deal with user inputs.

## Apllication starts from GfgCatalogServiceApplication.java

## Product and ProductVersion are persistence entities and ProductRepository and ProductVErsionRepository are jpa provider repositories (Spring data jpa).

## ProductService and ProductVersionService are service classes and other builders are supporting classes.

## A controller delegate ProductControllerDelegate (additional service layer) used among service ProductService and ProductCatalogController to deal with input and outputs.


# Unit and Integration Testing -
Folder - src/test/java

For unit testing mockito is used to provide mock. 
Unit testing is performed layer by layer (bottom to up) which means, once service layer testing is done, mock service layer is used in deligate controller to evaluate desired outcome.

For Integration tests - 
Folder - src/integrationtest/java
Spring runner is used in "Transactional" mode.

# Project build -

a) mvn clean package
It will create gfg-productcatalog-services.jar in target folder.

b) java -jar gfg-productcatalog-services.jar
Execute it using command prompt. 

OR 

a)It is a SpringBoot project and can be simply executed as a java project from GfgCatalogServiceApplication.java class

# Build and run a Docker application
1. Creation of Docker file : create images with a Dockerfile, which lists the components and commands that make-up the package.
2. Update POM.xml : Copies the jar into the docker directory as part of the package build target
3. Build an image : docker build -t gfg-productcatalog-services .
4. Running a docker container ==> docker run -d -p 8080:8080 gfg-productcatalog-services
5. List of built Docker images ==> docker ps -a | docker image ls
6. Stopping Docker containers : docker stop <container_name or container_id>
7. Container Logs==> docker logs <container_name or container_id>











































