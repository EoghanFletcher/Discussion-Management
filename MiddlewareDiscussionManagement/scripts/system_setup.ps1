# CA1 Setup file
# Name: Eoghan Fletcher

# EC2

# Resource names
$VpcName = 'VPC_Discussion_Management_E_F'
$SubnetName = 'Subnet_Discussion_Management_E_F'
$IGWName = 'Internet_Gateway_Discussion_Management_E_F'
$RTName = 'Route_Table_Discussion_Management_E_F'
$SGNameDescription = 'Security_Group_Discussion_Management_E_F'

# Check for existing resources which have the specified names
$Vpc=(aws ec2 describe-vpcs --filter Name=tag:Name,Values=$VpcName | ConvertFrom-Json).Vpcs
if ( $Vpc.Count -ge 1 ) { throw "Found $($Vpcs.Count) VPC already exists, please rename or delete this VPC and associated attributes including: Subnet, Internet Gateway, Route Table and Security Group" }
else { Write-Host 'The specified VPC was not found, creating resources' -Foreground Green }

    # Create Resources
    # Create VPC
    Write-Host 'Create VPC'
$Vpc=(aws ec2 create-vpc --cidr-block 10.0.0.0/16 | ConvertFrom-Json).Vpc
aws ec2 create-tags --resources $Vpc.VpcId --tags Key=Name,Value=$VpcName

    # Create Subnet                                 # Cidr-block accepts traffic within the specified range
    Write-Host 'Create Subnet'
$Subnet=(aws ec2 create-subnet --vpc-id $Vpc.VpcId --cidr-block 10.0.1.0/24 | ConvertFrom-Json).Subnet
aws ec2 create-tags --resources $Subnet.SubnetId --tags Key=Name,Value=$SubnetName

    # When the instance launches map a public IP Address
    Write-Host 'Modify Subnet - Map Public IP On Launch'
aws ec2 modify-subnet-attribute --subnet-id $Subnet.SubnetId --map-public-ip-on-launch

    # Create Internet Gateway
    Write-Host 'Create Internet Gateway'
$IGW=(aws ec2 create-internet-gateway | ConvertFrom-Json).InternetGateway
aws ec2 create-tags --resources $IGW.InternetGatewayId --tags Key=Name,Value=$IGWName

    # Attach the Internet Gateway to the previously created VPC
    Write-Host 'Attach Internet Gateway'
aws ec2 attach-internet-gateway --vpc-id $Vpc.VpcId --internet-gateway-id $IGW.InternetGatewayId

    # Get Route table Id
    Write-Host "Describe routes"
$RT = (aws ec2 describe-route-tables --filters Name=vpc-id,Values=$($Vpc.VpcId) | ConvertFrom-Json).RouteTables[0]
aws ec2 create-tags --resources $RT.RouteTableId --tags Key=Name,Value=$RTName

    # This route and Cidr-block accepts traffic beyond the range of the previously created route and Cidr-block, which where created when the VPC was created
    Write-Host 'Create New Route'
aws ec2 create-route --route-table-id $RT.RouteTableId --destination-cidr-block 0.0.0.0/0 --gateway-id $IGW.InternetGatewayId

    # Create Security Group
    Write-Host 'Create Security Group'
$SG=(aws ec2 create-security-group --vpc-id $Vpc.VpcId  --group-name $SGNameDescription --description $SGNameDescription --vpc-id $Vpc.VpcId | ConvertFrom-Json)

    # Authorise SSH, for all traffic
    Write-Host 'Open Port 22'
aws ec2 authorize-security-group-ingress --group-id $SG.GroupId --cidr 0.0.0.0/0 --protocol tcp --port 22

    # Authorise HTTP, for all traffic
    Write-Host 'Open Port 80'
aws ec2 authorize-security-group-ingress --group-id $SG.GroupId --cidr 0.0.0.0/0 --protocol tcp --port 80
aws ec2 authorize-security-group-ingress --group-id $SG.GroupId --cidr 0.0.0.0/0 --protocol tcp --port 8080

    # Authorise HTTPS, for all traffic
    Write-Host 'Open Port 443'
aws ec2 authorize-security-group-ingress --group-id $SG.GroupId --cidr 0.0.0.0/0 --protocol tcp --port 443
    Write-Host ''

    # Authorise HTTPS, for all traffic
    Write-Host 'Open Port 8080'
aws ec2 authorize-security-group-ingress --group-id $SG.GroupId --cidr 0.0.0.0/0 --protocol tcp --port 8080
    Write-Host ''


    # Run instance      
    Write-Host 'Run instance - Supplied User Data Script'                                                                                         # --image-id Amazon Linux 2
$RunInstance=(aws ec2 run-instances --instance-type t2.nano --security-group-ids $SG.GroupId --subnet-id $Subnet.SubnetId --image-id resolve:ssm:/aws/service/ami-amazon-linux-latest/amzn2-ami-hvm-x86_64-gp2 --key-name vockey --user-data file://user_data.sh  | ConvertFrom-Json).Instances


    # Get Public IP Address
    Write-Host 'Get Public IP Address'
$PublicIp=(aws ec2 describe-instances --instance-ids $RunInstance.InstanceId | ConvertFrom-Json).Reservations.Instances.PublicIpAddress
Write-Host ''

# Global Variables
Write-Host 'EC2 instance variables'
Write-Host 'The following PowerShell variables are available globally: $VpcId, $SubnetId, $RTId, $SGId, $IGWid, $Instance, $PublicIpAddress'
Write-Host ''

$global:VpcId=$Vpc.VpcId
$global:SubnetId=$Subnet.SubnetId
$global:RTId=$RT.RouteTableId
$global:SGId=$SG.GroupId
$global:IGWId=$IGW.InternetGatewayId
$global:Instance=$RunInstance.InstanceId
$global:PublicIpAddress=$PublicIp

Write-Host "vpc-id $($VpcId)"
Write-Host ''
Write-Host "subnet-id $($SubnetId)"
Write-Host ''
Write-Host "internet-gateway-id $($IGWid)"
Write-Host ''
Write-Host "route-table-id $($RTId)"
Write-Host ''
Write-Host "security-group-id $($SGId)"
Write-Host ''
Write-Host "instance $($Instance)"
Write-Host ''
Write-Host "public-ip-address $($PublicIpAddress)"
Write-Host ''

Write-Host 'S3 variables'
Write-Host 'The following PowerShell variables are available globally: $BucketLocation, $BucketName'
Write-Host ''

$global:BucketLocation=$Bucket.Location
$global:BucketName=$MyBucket

Write-Host "bucket-location $($BucketLocation)"
Write-Host "bucket-name $($BucketName)"

Write-Host ''
Write-Host "Please paste the following into the AWS Console:   ssh ec2-user@$($PublicIpAddress) -i ~/.ssh/labsuser.pem" -Foreground Blue
Write-Host ''
Write-Host "System Setup Complete" -Foreground Green