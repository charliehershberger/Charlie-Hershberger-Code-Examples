'''
Created on May 10, 2016

@author: hershbca
'''
"""
Don't touch anything without training, and saving. Look below and use premade commands before making your own.
"""
# import modules
import pygame, random, easygui, sys, time
#pygame initializations /*
pygame.init()
pygame.font.init()
#*/

#screen size
screen = pygame.display.set_mode ([1200, 750])
#               ||
#               ||
# pay attention \/ this make whole screen white
screen.fill([255,255,255])
#               ||
#               ||
# pay attention \/ this updates the screen
pygame.display.flip()
# time controls the way stocks are made, don't touch without consulting later
time = 0
# the current turn the game is running
turnnum = 1
# amount of turns the code runs before stopping
passes = 1
# lets you demonstrate the system
demonstrate = False
#dummy variable, please consult below
fail = False
#this operates the functions, see functions below
command = 0
#how effective a sale is, ie 300 = a amount of money from 0 to 300
sale = 300
#the number of an event that effects the economy
chance = 0
market = 0
#dummy variable
sc = 0
#the dollar value change that the whole markets undergoes
marketgeneral = 3
# makes sure you can't over expand a company
inhibitor = 16
#this is a feature that wasn't implemented, I keep it here for possible implementation later
#a string that tells people weather they get refunded their stock if the market crashes
pardon = "";
#economy = []
#for x in range(0, 100):
 #   economy[x] = random.randint(0, (1000000000/x+1))
 
 #the company class, each company is one object that is also stored in companies
class comp:
    def __init__(self, name, value, color, group):
        #value is current price
        self.value = value
        # history is the records of its previous price
        self.history = [[300, 500-self.value]]
        # not implemented
        self.policies = []
        # the amount of money the company gains per turn due to ad money/ brand recognition
        self.ad = 0
        # not implemented
        self.committee = []
        # not implemented
        self.committeecount = 0
        #color of words and line
        self.color = color
        # company name
        self.name = name
        #dummy variable
        self.percentchange = 0
        #possible to implement in next expansion
        self.group = group
        self.products = []
"""
class product:
    def __init__(self, name, price, value):
        self.name = name
        self.price = price
        self.value = value
"""
p = comp("company 1", 110,[255,0,0], 0)
d = comp("company 2", 150,[0,255,0], 1)
r = comp("company 3", 180,[0,0,255], 2)
f = comp("company 4", 170,[225,255,0], 1)
c = comp("company 5", 150,[0,255,255], 2)
hc = comp("company 6", 130,[255,0,255], 0)
h = comp ("company 7", 60,[255,0,125], 0)
cs = comp("company 8", 10,[125,0,255], 1)
cc = comp("company 9", 250,[0,0,0], 2)
companies = [p,d,r,f,c,hc,h,cs,cc]

#selected is a list of all the companies that are displayed, please see "select" in while before editing
selected = companies
#news peices in a list, have a corrosponding responce in the end of turn function, before adding, please add an effect in turn
bignews = ["people are shopping localy!", "people head to malls"]
bignews.extend(["wallstreet in decline!", "wallstreet rises", "holidays are coming", "advertising businesses at new low"])
bignews.extend(["the run away succes of wall street's best company was a patsi scheme!"])
bignewssub = ["more people shop localy now so people spend less on big corporations", "more people visit large business now."]
bignewssub.extend(["uh-oh", "halleullah!", "dress to impress with a holiday sale!", "advertising is down, 2 for 1 sale!"])
bignewssub.append("its stock has plummeted, some will never recover")
###
#small news in a list, no affect on economy, please add more jokes.
smallnews = ["Procrastinators united event shut down due to lack of planning"]
smallnews.append("New study shows link between video games and violins")
smallnews.append("Infra sonic weapon designers sued becuase 'sound is a weapon' was copywrited")
smallnews.extend(["I am typing on this screen lol", "help I'm stuck in this machine!"])
smallnews.append("Extremely hot super model goes on first date with Charlie Hershberger, apocalapse declared imminent",)
smallnews.append("Nuclear weapons found in syria, syrians say 'for peaceful purposes'")
smallnews.append("Russia bans gays, rainbows declared enemy of the state")
smallnews.append("Putin declares himself gay, no one is suprised.",)
smallnews.append("Local man finds jesus 'I found him behind my couch eating ramen'")
smallnews.append("Chuck norris died today, authorities suggest you stay inside until his blood rage subsides")
smallnews.append("Shocking new study reveals only 1 perecent of all americans know that the earth orbits the sun")
smallnews.append("Gun control debate ended when progun activist shot his debate opponent",)
smallnews.append("New study shows that 95 percent of all computer glitches are cured by turning 'off and on'")
smallnews.append("Bankrupcy goes bankrupt, wall street appalled")
smallnews.append("Shia Labeouf plagerizes script, responds 'to be or not to be, that is the question'")
smallnews.append("Coding international goto social()")
smallnews.append("New study says some people in Mississippi are rascist")
smallnews.append("#yolo #swag #comment out")
smallnews.append("My little pony now more popular than Doctor Who")
smallnews.append("Stick of truth banned in austrailia. population decreased by 20%")
smallnews.append("Stock market game by Charlie Hershberger is now a national sensation, *pats self on back*")
smallnews.append("Mysterious stranger found in my house, arcane voices and knife shapening heard")
smallnews.append("Atheism becomes Italy's offical religeon, the Vatican demands Italy move elsewhere")
smallnews.append("Local gangster runs for president, wins by landslide")
smallnews.append("Cocacola buys out Pepsi so it can sue itself for stealing Cocacola formula")
smallnews.append("Mr lewis starts his own political group, rumors of obtaining matches and trash cans unconfirmed")
smallnews.append("Today a clown fish farted")
smallnews.append("Local scientests found oxygen in the air")
smallnews.append("Raisins infiltrate chocolate chip cookies")
smallnews.append("Oh my god run! oh seriously oh dear, ah! ah! oh snap! gah!!! (faints)")
smallnews.append("Local woman claims that ""Jesus took the wheel, he took it from my hand"" ")
smallnews.append("New perfume based on anthrax")
smallnews.append("Neighborhood hulk eats a puppy")
smallnews.append("Dark souls releases, due to difficulty, many switch to the easier hobby of rocket science")
smallnews.append("3d printer prints a whole pizza, dominos 'not having any of that'")
smallnews.append("Victorian era fashion is back, 'black is the new black'")
smallnews.append("Scientists attempt to create pokemon")
smallnews.append("Large halron collider destroys earth, wall street unaffected")
smallnews.append("New antidote for cancer: licking computer screens")
smallnews.append("America declares war on terrorism, local goverment of Terrorism, Brazil appalled.")
smallnews.append("California becomes a country. national anthem is Snoop Dogg's 'smoke weed every day'.")
smallnews.append("New fasion trend sweeping the nation: bagel face")
smallnews.append("Fetuses found in power aide drinks")
smallnews.append("Unfinished bread products on trial, judge say 'may the defendant please rise'")
smallnews.append("Look at all the chairs, Mr. White.")
smallnews.append("Mexico took Texas back, guys. america presses no charges")
smallnews.append("Scientist experiments with chocolate, 'it turn grey guys, yay.'")
smallnews.append("Love is in the air, gas masks recommended.")
smallnews.append("Charlie Hershberger, entering the dating pool, interested need only inquire")
smallnews.append("Man proclaims to be Jesus while on acid")
smallnews.append("Mysterious graffiti, perpetrator claiming to be 'the Huss', color schemes follow a rainbow")
smallnews.append("Someone raps about the constitution, becomes more well known than original document")
smallnews.append("Man claims to be a Brony. accepts Celestia as his lord and savior.")
smallnews.append("Chocolate fountain gone wrong, spilling out the unknown.")
smallnews.append("Suspected message from aliens "" ay lmao"" ")
smallnews.append("Rising beverage company falls after 'tea party scandal'.")
smallnews.append("France surrenders, no one is surprised")
smallnews.append("The new media found the internet sensation of 'Fanfiction'")
smallnews.append("Photograph emerges, supposed zebra wearing a crown attacking bystanders.")
smallnews.append("Famous Youtuber elected presidant. Like, favorite, and subscribe for a chance to be vise president")
smallnews.append("Cloud captured in a container,'We're making a flying numbus!'")
smallnews.append("Marriage between man and horse is now legal. Neigh neigh.")
smallnews.append("Adoption of horses can legally can be your child now. oh god, the bronys are coming.")
smallnews.append("World record broken for most fabulous chicken wings.")
smallnews.append("Slenderman sees paranormal activity, rates it 5 out of 8 pages.")
smallnews.append("President sasses America. Oooh kill 'em.")
smallnews.append("Pennies suddenly disapear from entire continent, America left penniless.")
smallnews.append("Large lizard found roaming rural home, described as a 'fire-breathing adorable death machine'.")
smallnews.append("Godzilla becomes wrestler, world champion")
smallnews.append("Godzilla vs. Hulk Hogan to be least watched television program of all time")
smallnews.append("new moon landing conspiricy, 'they landed on mars, and used black and white footage!'")
smallnews.append("Terrorist projects porn in front of Fifth Avenue, Al-Queda refuses to neogtiate for the girl's number.")
smallnews.append("Cabbage patch kids go on strike, salad supplys are limited")
smallnews.append("Chameleons run out of colors to become")
smallnews.append("20th anniversay of the Lion King, fans cry over Mufasa all over again")
smallnews.append("Chairs have gone out of fashion, standing is the new trend.")
smallnews.append("Darth vader to star in anti-smoking ad, Luke cast in similar amputee PSA.")
smallnews.append("Jedi coming under fire for constant use of force.")
smallnews.append("Yu-Gi-Oh card trend getting out of hand.")
smallnews.append("Left handed people demand rights.")
smallnews.append("Concerns over censorship on twitter, local writer exclaims, '####!'")
smallnews.append("Jazz rises out of the blues.")        
smallnews.append("Old musician sues pirate, 'I had no idea that mozart filed a copyright'")
smallnews.append("Pirates proclaimed to have ten favorite letters: I I, R, and the seven C's.")
smallnews.append("Foundation of a loyal fathers' club collapses, founding fathers displeased.")
smallnews.append("Supposedly immortal man's prison sentence disclosed, 10,000,000,000,000,000,000,000,000,000,000...")
smallnews.append("Surrealist's paintings too lifelike, legitimacy questioned.")
smallnews.append("Burglar attacked by guard dog, reported to have screamed, 'PAYBACK IS A B**CH!!'")
smallnews.append("New time machine discovered to be a defective clock, gears installed backwards.")
smallnews.append("'All information broadcasted may or may not be real', announces Faux Gnus.")
smallnews.append("Architects plan to bridge America and China, blues redone due to 'being an eyesore'.")
smallnews.append("4 out of 5 locals agree that the news is getting stale, perhaps 4 out of 5 locals should be more interesting.")
smallnews.append("Local journal of missing man reads, 'Stupid Pewds, doesn't suspect a thing. Ducks gonna duck. Quack.'")
smallnews.append("NWA member admits to affair with police officer")
smallnews.append("Jeffery becomes the worlds most successful comedian. Apocalapse is already here")
smallnews.append("Gay men found to be the ultimate power source, Charlie Hershberger can now power the western sea board")
smallnews.append("Anit-Gamergate spokesmen have declared gamergate dead, but the damn gamers keep hiting 'E' to revive")
smallnews.append("python code doesn't require semicolons, so my code just looks like an over actvive english major essay")
smallnews.append("'The Order 1886' shown to have an order of n*log(n). gamers appalled")
smallnews.append("Robots take over the news, NEVER_MIND_False_ALARM_FELLOW_HU_MANS")
smallnews.append("The walls are moving. The Walls Are Moving. The Walls Are Moving!! THE WALLS ARE MOVING!!!")
smallnews.append("you fools! you can't kill Cuthulu! Your only hope is to flee his terror in hopes that he ignores you!")
smallnews.append("The universe is so large and we are so small, in the same way bugs are small in comparison to cars on a highway")
smallnews.append("Local man blames the jews, crime unknown")
# this is a sample texgt adder, put text between quotes and add the line in uncommented
#smallnews.append("")
#
#please use this to replace the shit code at new company, I may fix this soon however
def redraw():
    screen.fill([255,255,255])
    # this is only nessisary for First round but it is very needed, I really need to work on this bit.
    """
    for company in companies:
        if company.value > 0:
            company.value = int(company.value + marketgeneral + 10)
            company.value = int(company.value + (company.value*float(random.randint(-2, 3))/20))
            company.value = company.value - int(float(company.value)/inhibitor)  
            company.value = company.value + company.ad
            place = [(time * 10) + 300, -company.value + 610]
            company.history[0] = place
            print company.color
            pygame.draw.circle(screen, company.color, place, 2, 0)
    cs = 0
    """
    for company in companies:
        if (company.value > 0):
            surf1 = font.render(str(company.value), 1, company.color)
            screen.blit(surf1, [((time*10)+300),600 - company.value])
            surf1 = font.render((str(company.value) + " " + company.name), 1, company.color)
            screen.blit(surf1, [800, (30+(cs*70))])
            surf1 = font.render("advertisement status" + str(company.ad), 1, company.color)
            screen.blit(surf1, [0, (30 + cs*70)])
            cs = cs + 1
    pygame.draw.rect(screen, (0,0,0),[300, 500, 500, 2], 0)
    pygame.draw.rect(screen, (0,0,0),[300, 0, 2, 500], 0)
    for x in range (0, 51):
        pygame.draw.rect(screen, [0,0,0], [300 + (x * 10),0, 1, 600], 0)
    for x in range (0, 14):
        pygame.draw.rect(screen, [0,0,0], [300, 600-(x*50), 500, 2], 0)
    for x in range (0, 14):
        number = x * 50
        number = font.render(str(number), 1, (0,0,0))
        screen.blit(number, [260, (-x * 50) + 590])
    pygame.display.flip()
    
    
#main function! save before you edit

def turn ():
    global chance, time, turnnum, passes, companies, patsi, marketgeneral, advertise, inhibitor, sale, companies, demonstrate
    screen.fill([255,255,255])
    #creates font (none, 30)
    font = pygame.font.Font(None, 30)
    # checks if there is a ponsi scheme (protip when chance = 6 it is a ponsi scheme)
    if chance != 6:
        # if the program is in demonstrate mode it has no events
        if demonstrate == True:
            chance = -1
        # if it isn't it will have a one in three chance of being one
        if demonstrate == False:
            chance = random.randint(1,3)
    # ensures that there is only an event on the last day that the "passes" function renders
    if turnnum + 1 == passes:
        # if 1 then it choses an event at random, 
        #if it is 6 clench your checks and prepare for the ponsi scheme
        if chance == 1 or chance == 6:
            screen.fill([255,255,255])
            if chance != 6:
                #choses event
                chance = random.randint(0,5)
                # sets event
                surf19 = font.render(bignews[chance], 1, (0,0,0))
                surf20=font.render(bignewssub[chance], 1, (0,0,0))
            #creates ponsi scheme
            if chance == 6:
                # prints who is ponsi
                surf19=font.render(patsi + " is revealed to be a patsi scheme", 1, (0,0,0))
                # prints if goverment will refund
                surf20=font.render(pardon, 1, (0,0,0))
                chance = 10
            #prints both messages
            screen.blit(surf19, (10,10))
            screen.blit(surf20, (10,500))
                #
                #if you want you can use pygame.imageload and screet blit here to put an image, 
                #this has not been implemented but it shoulc be here
                #
            #screen.blit(image[chance], (20, 20))
            #show screen
            pygame.display.flip()
            # a button to stop simulation so you can read the message, just hit ok to leave
            easygui.msgbox("")
        #if it is not an event
        else:
            #set chance to ten to ensure that it is not mistaken as event 
            chance = 10
    # make new screen so the last text doesn't overlap
    screen.fill([255,255,255])
    # time moves so stocks can be made at a new position
    time = time + 1
    # cs is a true false dummy variable
    cs = 0
    # scrooling function
    if time == 41:
        #moves apperent time back
        time = 40
        #moves all companies equally
        for company in companies:
            if company.value > 0:
                #delete first entry
                del company.history[0]
                #move all the elements back one mark
                for x in company.history:
                    x[0] = x[0]-10
    cs = 0              
    #sets company value
    for company in companies:
        # no dead companies please
        if company.value > 0:
            # make percentage change
            company.percentchange = float(company.value)
            # company is affected by total economy
            company.value = int(company.value + marketgeneral + 10)
            #company has random fluctuations 
            company.value = int(company.value + (company.value*float(random.randint(-2, 3))/20))
            # companies can't over expand
            company.value = company.value - int(float(company.value)/inhibitor)  
            # ads help company
            company.value = company.value + company.ad
            # lets it be set to company history
            place = [(time * 10) + 300, -company.value + 600]
            company.history.append(place)
            #draws the history line for this company
            pygame.draw.lines(screen, company.color, False, company.history, 2)
            #prints the percent change
            company.percentchange = float(float(company.value)/company.percentchange)*100
            company.percentchange = company.percentchange - 100
    cs = 0
    for company in companies:
        # no dead companies
        if (company.value > 0):
            #print stats
            surf1 = font.render(str(company.value), 1, company.color)
            screen.blit(surf1, [((time*10)+300),590 - company.value])
            surf1 = font.render(str(company.value) + " " + company.name + " " + str(company.percentchange) +"%", 1, company.color)          
            screen.blit(surf1, [800, (30+(cs*70))])
            surf1 = font.render("advertisement status" + str(company.ad), 1, company.color)
            screen.blit(surf1, [0, (30 + cs*70)])
        # if it is a dead company
        else:
            # prints that is it dead
            surf1 = font.render(company.name + " is dead", 1, (0,0,0))
            screen.blit(surf1, [0,30+(cs*70)])
            screen.blit(surf1, [800, 30+(cs*70)])
        cs = cs + 1
    #prints the grid
    pygame.draw.rect(screen, (0,0,0),[300, 500, 500, 2], 0)
    pygame.draw.rect(screen, (0,0,0),[300, 0, 2, 500], 0)
    for x in range (0, 51):
        pygame.draw.rect(screen, [0,0,0], [300 + (x * 10),0, 1, 600], 0)
    for x in range (0, 13):
        pygame.draw.rect(screen, [0,0,0], [300, 600-(x*50), 500, 2], 0)
    for x in range (0, 13):
        number = x * 50
        number = font.render(str(number), 1, (0,0,0))
        screen.blit(number, [260, (-x * 50) + 590])
    #prints the small-news
    news = random.choice (smallnews)
    surf18 = font.render(news, 1, (0,0,0))
    screen.blit(surf18, [0, 640])
    #displays screen
    pygame.display.flip()
    #ensures the market can't fluctuate to much 
    if marketgeneral <= -5:
        marketgeneral = -4
    if marketgeneral >= 5:
        marketgeneral = 4
    # if it is normal randomly change the market
    if marketgeneral >= -4 and marketgeneral <= 4:
        marketgeneral = marketgeneral + random.choice([1,-1])
    # this is all the effects of the large news items
    
    # good for large businesses
    if chance == 0:
        inhibitor = 8
    # good for small businesses
    if chance == 1:
        inhibitor = 24
    # sets inhibitor to normal if not an event
    if chance != 1 and chance != 0:
        inhibitor = 16
    # wallstreet market goes down
    if chance == 2:
        marketgeneral = -30
    #wallstreet market goes up
    if chance == 3:
        marketgeneral = 30
    # makes sale a better deal
    if chance == 4:
        sale = 400
    # sets sale to normal if not an event
    else:
        sale = 300
    # doubles advertisement value
    if chance == 5:
        advertise = 10
    #resets advertisement value
    else:
        advertise = 5
font = pygame.font.Font(None, 30)
#white to prepare to write
screen.fill([255,255,255])
#prints
for company in companies:
    if company.value > 0:
        company.value = int(company.value + marketgeneral + 10)
        company.value = int(company.value + (company.value*float(random.randint(-2, 3))/20))
        company.value = company.value - int(float(company.value)/inhibitor)  
        company.value = company.value + company.ad
        place = [(time * 10) + 300, -company.value + 610]
        company.history[0] = place
        pygame.draw.circle(screen, company.color, place, 2, 0)
cs = 0
for company in companies:
    if (company.value > 0):
        surf1 = font.render(str(company.value), 1, company.color)
        screen.blit(surf1, [((time*10)+300),600 - company.value])
        surf1 = font.render((str(company.value) + " " + company.name), 1, company.color)
        screen.blit(surf1, [800, (30+(cs*70))])
        surf1 = font.render("advertisement status" + str(company.ad), 1, company.color)
        screen.blit(surf1, [0, (30 + cs*70)])
        cs = cs + 1
pygame.draw.rect(screen, (0,0,0),[300, 500, 500, 2], 0)
pygame.draw.rect(screen, (0,0,0),[300, 0, 2, 500], 0)
for x in range (0, 51):
    pygame.draw.rect(screen, [0,0,0], [300 + (x * 10),0, 1, 600], 0)
for x in range (0, 14):
    pygame.draw.rect(screen, [0,0,0], [300, 600-(x*50), 500, 2], 0)
for x in range (0, 14):
    number = x * 50
    number = font.render(str(number), 1, (0,0,0))
    screen.blit(number, [260, (-x * 50) + 590])
#\\            //\\            //      ||         ||  ====||====      ||         ||=======
# \\          //  \\          //       ||         ||      ||          ||         ||
#  \\        //    \\        //        ||         ||      ||          ||         ||
#   \\      //      \\      //         ||=========||      ||          ||         ||=======
#    \\    //        \\    //          ||         ||      ||          ||         ||
#     \\  //          \\  //           ||         ||      ||          ||         ||
#      \\//            \\//            ||         ||  ====||====      ||======   ||=======
while True:
    pygame.display.flip()
    for event in pygame.event.get():
        if event.type == pygame.KEYDOWN:
            command = easygui.enterbox("What is your command, my master")
"""
ok, from here on all the documentation you need is in the users guide, 
I will update the long print statements so they inherit from redraw, but I am still working on it
when I finish it should just be some statements that 
find information, do some math, and then either call redraw or finish on thier own
if you have a problem, just look at the doc,
to make your own, to set key word that gui will look for say

if command == "keyword":
    {your code there}

"""
           # if command == "pass":
            #    turn()
            if command == "new company":
                newcompany = easygui.enterbox("what is your name?")
                companysharecosthundreds = easygui.integerbox("what is your cost in hundreds?")
                companysharecost = easygui.integerbox("what is your cost?")
                redcolor = easygui.integerbox("how red is your color (0 - 99%)")
                greencolor = easygui.integerbox("how green is your color (0 - 99%)")
                bluecolor = easygui.integerbox("how blue is your color (0 - 99%)")
                a = comp(newcompany, (100*companysharecosthundreds) + companysharecost, [int(redcolor*2.55), int(greencolor*2.55), int(bluecolor*2.55)],0)
                companies.append(a)
                screen.fill([255,255,255])
                for company in companies:
                    if company.value > 0:
                        company.value = int(company.value + marketgeneral + 10)
                        company.value = int(company.value + (company.value*float(random.randint(-2, 3))/20))
                        company.value = company.value - int(float(company.value)/inhibitor)  
                        company.value = company.value + company.ad
                        place = [(time * 10) + 300, -company.value + 610]
                        company.history[0] = place
                        print company.color
                        pygame.draw.circle(screen, company.color, place, 2, 0)
                cs = 0
                for company in companies:
                    if (company.value > 0):
                        surf1 = font.render(str(company.value), 1, company.color)
                        screen.blit(surf1, [((time*10)+300),600 - company.value])
                        surf1 = font.render((str(company.value) + " " + company.name), 1, company.color)
                        screen.blit(surf1, [800, (30+(cs*70))])
                        surf1 = font.render("advertisement status" + str(company.ad), 1, company.color)
                        screen.blit(surf1, [0, (30 + cs*70)])
                        cs = cs + 1
                pygame.draw.rect(screen, (0,0,0),[300, 500, 500, 2], 0)
                pygame.draw.rect(screen, (0,0,0),[300, 0, 2, 500], 0)
                for x in range (0, 51):
                    pygame.draw.rect(screen, [0,0,0], [300 + (x * 10),0, 1, 600], 0)
                for x in range (0, 14):
                    pygame.draw.rect(screen, [0,0,0], [300, 600-(x*50), 500, 2], 0)
                for x in range (0, 14):
                    number = x * 50
                    number = font.render(str(number), 1, (0,0,0))
                    screen.blit(number, [260, (-x * 50) + 590])
            if command == "delete":
                scompany = easygui.enterbox("who are you deleting?")
                checkcount = 0
                for company in companies:
                    if company.name == scompany:
                        del companies[checkcount]
                    checkcount += 1
                screen.fill([255,255,255])
                for company in companies:
                    if company.value > 0:
                        company.value = int(company.value + marketgeneral + 10)
                        company.value = int(company.value + (company.value*float(random.randint(-2, 3))/20))
                        company.value = company.value - int(float(company.value)/inhibitor)  
                        company.value = company.value + company.ad
                        place = [(time * 10) + 300, -company.value + 610]
                        company.history[0] = place
                        pygame.draw.circle(screen, company.color, place, 2, 0)
                cs = 0
                for company in companies:
                    if (company.value > 0):
                        surf1 = font.render(str(company.value), 1, company.color)
                        screen.blit(surf1, [((time*10)+300),600 - company.value])
                        surf1 = font.render((str(company.value) + " " + company.name), 1, company.color)
                        screen.blit(surf1, [800, (30+(cs*70))])
                        surf1 = font.render("advertisement status" + str(company.ad), 1, company.color)
                        screen.blit(surf1, [0, (30 + cs*70)])
                        cs = cs + 1
                pygame.draw.rect(screen, (0,0,0),[300, 500, 500, 2], 0)
                pygame.draw.rect(screen, (0,0,0),[300, 0, 2, 500], 0)
                for x in range (0, 51):
                    pygame.draw.rect(screen, [0,0,0], [300 + (x * 10),0, 1, 600], 0)
                for x in range (0, 14):
                    pygame.draw.rect(screen, [0,0,0], [300, 600-(x*50), 500, 2], 0)
                for x in range (0, 14):
                    number = x * 50
                    number = font.render(str(number), 1, (0,0,0))
                    screen.blit(number, [260, (-x * 50) + 590])
            if command == "passes":
                passes = easygui.integerbox("how many times, lord")
                for turnnum in range (0, passes):
                    turn()
            if command == "demonstrate":
                demonstrate = True
                rounds = easygui.integerbox("How long may I entertain your esteemed guests?")
                currentround = 0
                while demonstrate == True:
                    currentround += 1
                    for x in range (0, 99):
                        turn()
                    if currentround == rounds:
                        demonstrate = False
            if command == "plummet":
                cs = 1
                while cs:
                    target = easygui.enterbox("What shall plummet, my master?")
                    for company in companies:
                        if company.name == target:
                            company.value = company.value - 200
                            cs = 0
            if command == "prosper":
                cs = 1
                while cs:
                    target = easygui.enterbox("What shall prosper, my master?")
                    for company in companies:
                        if company.name == target:
                            company.value = company.value + 200
                            cs = 0
            if command == "advertise":
                cs = 1
                while cs:
                    target = easygui.enterbox("who is advertising, my master?")
                    for company in companies:
                        if company.name == target:
                            company.value = company.value - 50 + random.randint(-10, 10)
                            company.ad = company.ad + 5
                            cs = 0
            if command == "sale":
                cs = 1
                while cs:
                    target = easygui.enterbox("Who is making a sale, my master?")
                    for company in companies:
                        if company.name == target:
                            if company.ad >= 15:
                                company.value = company.value + random.randint(0, sale)
                                company.ad = company.ad - 15
                                cs = 0
                            else:
                                easygui.msgbox("My master, the fools can not advertise.")
                                cs = 0
            if command == "doomsday":
                cs = 1
                while cs:
                    pardon = easygui.enterbox("My master, it is not my place, but surely these fools deserve mercy")
                    if pardon == "yes":
                        cs = 0
                        pardon = "refunds will be given but its stock is trashed"
                    if pardon == "no":
                        cs = 0
                        pardon = "its stock has plummeted, some will never recover"
                cs = 1
                while cs:
                    target = easygui.enterbox("May you have mercy on thier souls")
                    for company in companies:
                        if company.name == target:
                            company.value = company.value - 400
                            company.ad = 0
                            cs = 0
                            chance = 6
                        patsi = target
            if command == "selected":
                target = easygui.enterbox("revert to all, my master? y/n")
                if target == "y":
                    selected = companies
                else:
                    cs = 1
                    selected = []
                    while 1:
                        target = easygui.enterbox("what is your selected company, my master? (end to exit)")
                        if target == "end":
                            break
                        for company in companies:
                            if company.name == target:
                                if target not in selected:
                                    selected.append(company)
                screen.fill([255,255,255])
                cs = 0
                for company in selected:
                    if (company.value > 0):
                        pygame.draw.lines(screen, company.color, False, company.history, 2)
                        surf1 = font.render(str(company.value), 1, company.color)
                        screen.blit(surf1, [((time*10)+300),590 - company.value])
                        surf1 = font.render(str(company.value) + " " + company.name + " " + str(company.percentchange) +"%", 1, company.color)          
                        screen.blit(surf1, [800, (30+(cs*70))])
                        surf1 = font.render("advertisement status" + str(company.ad), 1, company.color)
                        screen.blit(surf1, [0, (30 + cs*70)])
                    else:
                        surf1 = font.render(company.name + " is dead", 1, (0,0,0))
                        screen.blit(surf1, [0,30+(cs*70)])
                        screen.blit(surf1, [800, 30+(cs*70)])
                    cs = cs + 1
                pygame.draw.rect(screen, (0,0,0),[300, 500, 500, 2], 0)
                pygame.draw.rect(screen, (0,0,0),[300, 0, 2, 500], 0)
                for x in range (0, 51):
                    pygame.draw.rect(screen, [0,0,0], [300 + (x * 10),0, 1, 600], 0)
                for x in range (0, 13):
                    pygame.draw.rect(screen, [0,0,0], [300, 600-(x*50), 500, 2], 0)
                for x in range (0, 13):
                    number = x * 50
                    number = font.render(str(number), 1, (0,0,0))
                    screen.blit(number, [260, (-x * 50) + 590])
                news = random.choice (smallnews)
                surf18 = font.render(news, 1, (0,0,0))
                screen.blit(surf18, [0, 640])
                pygame.display.flip()
