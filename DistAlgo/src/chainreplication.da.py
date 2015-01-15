import sys, configparser, random, time
from enum import Enum, unique
from threading import Thread
class account(object):
       def __init__(self, accNum = 0, balance = 0):
               self.accountNum = accNum
               self.balance = balance

       def getBalance(self):
               return self.balance
       
       def deposit(self, amount):
               self.balance = self.balance + amount
       
       def withdraw(self, amount):
               if self.balance >= amount:
                      self.balance = self.balance - amount
                      return True
               else:
                      return False 			

class Outcome(Enum):
	Processed = 1
	InconsistentWithHistory = 2
	InsufficientFunds = 3

class RequestType(Enum):
	Deposit = 1
	Withdraw = 2
	GetBalance = 3
	Transfer = 4
	
class client(process):
	def setup(head, tail, requests, name, timeout, sleepafternumrequests, retransmitTimeout, clientignorerequests):
		self.name = name
		self.head = head
		self.tail = tail
		self.requests = requests	
		self.q = set()	
		self.recvd = set()
		self.clientignorerequests = clientignorerequests
		self.sleepafternumrequests = sleepafternumrequests
		self.retransmitTimeout = retransmitTimeout
		#output(self.name+ "chain size " + str(len(servers)))
		output(self.name + "no of requests to be sent is... " + str(len(requests)))
		self.timeout = timeout
		output(self.name + " set up called...")
		
	def mutex(rid,up,acc,am, task):
		--start
		reqc = logical_clock()
		q.add((reqc, self.id, rid))
		if up == RequestType.GetBalance:
			update = 'Query'
			output(self.name + " sending request to bank (reqid= " + rid + ", operation=" + update +", accNum=" + str(acc) + ", amount=" + str(am) + ")")
			send((rid, update, acc, am), to=tail)
				
		elif up == RequestType.Deposit:
			update = 'Deposit'
			output(self.name + " sending request to bank (reqid= " + rid + ", operation=" + update +", accNum=" + str(acc) + ", amount=" + str(am) + ")")
			send((rid, update, acc, am), to=head)
		else:
			update = 'Withdraw'
			output(self.name + " sending request to bank (reqid= " + rid + ", operation=" + update +", accNum=" + str(acc) + ", amount=" + str(am) + ")")
			send((rid, update, acc, am), to=head)
		else:
			update = 'Transfer'
			
	def main():
		def announce():
			output("in cs ")
		--start
		index = 0
		for (rid,up,acc,am,dstbank,dstacc) in requests:
			index = index + 1
			if index == self.sleepafternumrequests:
				time.sleep(timeout)
			if index > self.clientignorerequests:
				mutex(rid,up,acc,am,dstbank,dstacc,announce)
		#for (rid, up, acc, am) in requests:
		if await(received(('Reply',id, id, id), from_=tail)):
			pass
		elif timeout(self.retransmitTimeout):
			for (rid, up, acc, am) in requests:
				if rid not in recvd:
					output(self.name + " reply not received." + rid)
					mutex(rid,up,acc,am,dstbank,dstacc,announce)
				#if rid == None:
				#	output(self.name + "rid is none")
				#else:
				#	output(self.name + "resending request to server: " + rid)
				#	mutex(rid,up,acc,am,announce)
		--end
		#if await(received(('Reply',id, id, id)) or received(('newhead', id)) or received(('newtail', id))):
		if await(received(('newhead', id)) or received(('newtail', id))):
			output("received reply")

	def recv(msg=('newhead',head), from_=master):
		output(self.name + "new head")
		self.head = head
		
	def recv(msg=('newtail',tail), from_=master):
		output(self.name + "new tail")
		self.tail = tail		
	
	def recv(msg=('Reply',reqid, outcome,balance), from_=source):
		output(self.name + "reply from server: (reqid=" + reqid + ", outcome=" + str(outcome) + ",balance= " + str(balance))
		recvd.add((reqid))		

class server(process):
	def setup(name, role, successor, predecessor, bank, master, maxrequests, chainExtensionFailureMaxMsg, heartbeattimeoutserver):
		if role == None:
			time.sleep(5)
			output(self.name + "extended chain's new tail")
		self.name = name
		self.role = role	
		self.successor = successor
		self.predecessor = predecessor
		self.client = None
		self.pending = []
		self.completed = []
		self.bank = bank	
		self.outcomeHistory = {}
		self.heartbeattimeoutserver = heartbeattimeoutserver
		self.chainExtensionFailureMaxMsg = chainExtensionFailureMaxMsg
		output(self.name + "self.chainExtensionFailureMaxMsg  "  + str(self.chainExtensionFailureMaxMsg))
		self.maxrequests = maxrequests
		self.currentreqcount = 0
		#self.newMemberFlag = False
		output(self.name + "server set up called.. timeout " + str(maxrequests))
		#sendheartbeat()
		if self.predecessor == None and self.successor == None:
			output(self.name + "new server in chain ")
			setupExtendedServer()
			sendheartbeat()
		else:
			sendheartbeat()	
	def main():
		#if self.predecessor == None and self.successor == None:
		#	output(self.name + "new server in chain ")
		#	setupExtendedServer()
			#return
		--sync
		#while True:
		#	if await(False): 
		#		pass
		#	elif timeout(timeout):	
		#		#send heartbeat message
		#		#output(self.name + "sending heartbeat")
		#		#if self.role != 'tail':
		#		if self.currentreqcount < self.maxrequests:
		#			output(self.name + "heartbeat sending..")
		#			send(('heartbeat',), to=self.master)
		if await(received(('terminate',)) or received(('newsuccessor',id)) or received(('newpredecessor',id)) or received(('pendingtransactions',id))): 
			pass
		if await(received(('Ack',id,id,id,id,id,id))):pass
		if self.role == 'tail':
			if await(received((id, 'Query', id))): pass
		elif self.role == 'head':
			if await(received((id, 'Deposit',id,id)) or received((id, 'Withdraw',id,id))): 
				output(self.name + ":main received request from client")
		else:
			if await(received(('Sync',id,id,id,id,id,id))):pass
		#--release

	def sendheartbeat():
		while True:
			if await(False):
				pass
			elif timeout(self.heartbeattimeoutserver):
                                #send heartbeat message
				if self.currentreqcount < self.maxrequests:
					output(self.name + "heartbeat sending..")
					send(('heartbeat',), to=self.master)

	def setupExtendedServer():
		#inform master to join the chain
		output(self.name + "sent wanttojoin to master")
		send(('wanttojoin',self.bank), to=self.master)

	def recv(msg=('newjoinee',newtail), from_=m):
		output(self.name + "new member joining request")
		#self.newMemberFlag = True
		index = 0
		for (rid,up,ac,am,cl) in completed:
			size = len(completed)
			index = index + 1
			if index == self.chainExtensionFailureMaxMsg and self.chainExtensionFailureMaxMsg > 0:
				self.currentreqcount = 100000
				output(self.name + " crashed....")
				if self.currentreqcount > self.maxrequests:
					return
			send(('completedtransactions',rid,up,ac,am,cl,index, size), to=newtail)
			output(self.name + "index of current transacrion in completed list is " + str(index))	
		send(('bankdetail', bank, len(self.completed)), to =newtail)
		output(self.name + "pending list size " + str(len(pending)) + " completed list size " + str(len(completed)))
			
	def recv(msg=('bankdetail', b, maxsize), from_=oldtail):
		output(self.name + " maxsize " + str(maxsize) + " completed list size is " + str(len(self.completed)))
		if maxsize != len(self.completed):
			output(self.name +  "bank detail not completed")
			return
		self.bank = b
		#balance = bank.getBalance(123456)
		#output(self.name + " balance is " + str(balance))
		send(('synccomplete',self.bank), to=self.master)

	def recv(msg=('newsuccessor',newsuccessor), from_=m):
		self.successor = newsuccessor
		#send pending transactionlist to new successor	
		send(('pendingtransactions',self.pending), to=newsuccessor)	

	#def recv(msg=('completedtransactions',completedtrans), from_=prev):
	#	self.completed = completedtrans
	
	def recv(msg=('completedtransactions',rid,up,ac,am,cl,index, size), from_=source):
		if index == 0:
			self.completed = []
		if self.currentreqcount < self.maxrequests:
			self.completed.append((rid,up,ac,am,cl))
			self.currentreqcount = self.currentreqcount + 1
			output(self.name + "current request count is : " + str(self.currentreqcount))
		else:
			output(self.name + "crashing server")
			return

	#pending transactions from new predecessor
	def recv(msg=('pendingtransactions',pendingtrans), from_=m):	
		output(self.name + "received pending transation from predecesor")
		for (rid,up,ac,am,cl) in pendingtrans:
			output(self.name + "received pending trans reqids: " + rid)
		for (rid,up,ac,am,cl) in pending:
			output(self.name + "self pending trans reqids: " + rid)
		for (rid,up,ac,am,cl) in completed:
			output(self.name + "self completed trans reqids: " + rid)
		for (rid,up,ac,am,cl) in pendingtrans:
			#output(self.name + "pendingtransactions1")
			if (some((reqid, update, account, amount, client) in pending, has=((rid == reqid) and (up != update or ac != account or am != amount or cl != client)))):
				output(self.name + "inconsistent with history for reqid= " + reqid)
				outcome = Outcome.InconsistentWithHistory
				handlerequest(rid,up, ac, am, outcome, cl)
			elif (some((reqid, update, account, amount, client) in completed, has=((rid == reqid) and (up != update or ac != account or am != amount or cl != client)))):
				output(self.name + "inconsistent with history for reqid = " + reqid)
				outcome = Outcome.InconsistentWithHistory
				handlerequest(rid,up, ac, am, outcome, cl)
			elif (rid,up,ac,am,cl) not in pending and (rid,up,ac,am,cl) not in completed:
				output(self.name + "pendingtransactions3")
				outcome = Outcome.Processed
				handlerequest(rid,up, ac, am, outcome, cl)
			elif (rid,up,ac,am,cl) in pending and (rid,up,ac,am,cl) not in completed:
				#handle
				outcome = Outcome.Processed
				handlerequest(rid, up, ac, am, outcome, cl)
			elif (rid,up,ac,am,cl) not in pending and (rid,up,ac,am,cl) in completed:
				#send back ack
				(outcome, balance) = self.outcomeHistory[rid]
				output(self.name + "sending ack to predecessor for reqid = " + rid)
				send(('Ack',rid, up, ac, am, outcome, cl), to=self.predecessor)	
					
	def recv(msg=('newpredecessor',newpredecessor), from_=m):
                self.predecessor = newpredecessor

	def recv(msg=('rolechange',role), from_=m):
		self.role = role
		if role == 'head':
			output(self.name  + "new role:head")
			self.predecessor = None
		elif role == 'tail':
			output(self.name  + "new role:tail")
			self.successor = None
		elif role == 'normal':
			self.name = self.name + "::oldtail"
			output(self.name  + "new role:normal server")
	
	def recv(msg=('terminate',), from_=m):
                output(self.name + "terminate called..")
	
	#head deposit and withdraw requests from client
	def recv(msg=(reqid,update,accNum, amount), from_=client):	
		if self.currentreqcount < self.maxrequests:
			self.currentreqcount = self.currentreqcount + 1
			output(self.name + "current request count is : " + str(self.currentreqcount))
		else:
			output(self.name + "crashing server")
			return
		if update == 'Query':
			self.handleQuery(reqid, 'Query',accNum,amount,client)
			return
		output(self.name + "received request from client: (reqid= " + reqid + ", operation=" + update +", accNum=" + str(accNum) + ", amount=" + str(amount) + ")")
		outcome = None
		#check for duplicate requests
		if (reqid,update,accNum,amount,client) in completed or (reqid,update,accNum,amount,client) in pending:
			#output(self.name + "dulicate request")	
			(outcome,balance) = outcomeHistory[reqid]	
			#outcome = Outcome.Processed
			#even if duplicate add to pending queue
			pending.append((reqid,update,accNum,amount,client))
		elif (some((rid, up, ac, am, cl) in pending, has=((rid == reqid) and (up != update or ac != account or am != amount or cl != client)))):
			output(self.name + "inconsistent with history for reqid= " + reqid)
			outcome = Outcome.InconsistentWithHistory
		elif (some((rid, up, ac, am, cl) in completed, has=((rid == reqid) and (up != update or ac != account or am != amount or cl != client)))):
			output(self.name + "inconsistent with history for reqid = " + reqid)
			outcome = Outcome.InconsistentWithHistory
		else:
			pending.append((reqid,update,accNum,amount,client))	
			outcome = Outcome.Processed
			if update == 'Deposit':
				bank.deposit(accNum, amount)
			elif update == 'Withdraw':
				result = bank.withdraw(accNum, amount)
				if result == False:
					outcome = Outcome.InsufficientFunds
			outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
			#output(self.name + "processed request for reqid=" + reqid) 
		if self.role == 'head' and self.successor is None:
			balance = bank.getBalance(accNum)
			if outcome != Outcome.InconsistentWithHistory:
				pending.remove((reqid,update,accNum,amount,client))
				completed.append((reqid, update,accNum,amount,client))
				outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
			output(self.name + "sending reply to client: (reqid = " + reqid + ", outcome= " + str(outcome) + ", balance = " + str(balance) + ")")
			send(('Reply',reqid, outcome, balance), to=client)
		else: 
			output(self.name + "sending sync to successor for reqid = " + reqid)
			send(('Sync',reqid, update, accNum, amount, outcome, client), to=successor)

	#tail:: query or getbalance() request from client
	def handleQuery(reqid, query, accNum, junk,client):
		output(self.name + "shashi::received query request from client: (reqid =" + reqid + ", operation = Query, accNum =" + str(accNum) + ")")
		balance = bank.getBalance(accNum)
		output(self.name + "sending reply to client: (reqid = " + reqid + ", outcome= " + str(Outcome.Processed) + ", balance = " + str(balance) + ")")
		send(('Reply', reqid, Outcome.Processed, balance), to=client)	
	
	def handlerequest(reqid,update, accNum, amount, outcome, client):
		if self.currentreqcount < self.maxrequests:
			self.currentreqcount = self.currentreqcount + 1
			output(self.name + "current request count is : " + str(self.currentreqcount))
		else:
			output(self.name + " crashing server...")
			return
		output(self.name + "received sync : (reqid= " + reqid + ", operation=" + update +", accNum=" + str(accNum) + ", amount=" + str(amount) + ")")
		if outcome == Outcome.InconsistentWithHistory:
			output(self.name + "InconsistentWithHistory for reqid = " + reqid)
		elif(reqid,update,accNum,amount,client) not in completed and (reqid,update,accNum,amount,client) not in pending:
			self.pending.append((reqid,update,accNum,amount,client))
			if update == 'Deposit':
				self.bank.deposit(accNum, amount)
			elif update == "Withdraw":
				result = bank.withdraw(accNum, amount)
				if result == False:
					outcome = Outcome.InsufficientFunds
					#output(self.name + "InsufficientFunds for reqid=" + reqid)
				#else:
					#output(self.name + "processed request for reqid=" + reqid)
			self.outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
			#output(self.name + "processed request for reqid=" + reqid)
		else:
			(outcome, balance) = self.outcomeHistory[reqid]
			#outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
			self.pending.append((reqid,update,accNum,amount,client))
			send(('Reply',reqid,outcome,balance), to=client)
			send(('Ack',reqid, update, accNum, amount, outcome, client), to=self.predecessor)
			return
			#output(self.name + "duplicate request for reqid = " + reqid)
		if self.role == 'tail':
			if outcome != Outcome.InconsistentWithHistory:
				self.outcomeHistory[reqid] = (outcome,self.bank.getBalance(accNum))
				self.completed.append((reqid,update,accNum,amount,client))
				self.pending.remove((reqid,update,accNum,amount,client))
				output(self.name + "sending ack to predecessor for reqid = " + reqid)
				send(('Ack',reqid, update, accNum, amount, outcome, client), to=self.predecessor)
			balance = self.bank.getBalance(accNum)
			output(self.name + "sending reply to client: (reqid = " + reqid + ", outcome= " + str(outcome) + ", balance = " + str(balance) + ")")
			send(('Reply',reqid,outcome,balance), to=client)
		else:
			output(self.name + "sending sync to successor for reqid = " + reqid)
			send(('Sync',reqid,update, accNum, amount, outcome, client), to=self.successor)
		

	def recv(msg=('Sync',reqid,update, accNum, amount, outcome, client), from_=source):
		#if self.currentreqcount < self.maxrequests:
		#	self.currentreqcount = self.currentreqcount + 1
		#else:
		#	output(self.name + " crashing server...")
		#	return
		handlerequest(reqid,update, accNum, amount, outcome, client)
		#output(self.name + "received sync : (reqid= " + reqid + ", operation=" + update +", accNum=" + str(accNum) + ", amount=" + str(amount) + ")")
		#if outcome == Outcome.InconsistentWithHistory:
		#	output(self.name + "InconsistentWithHistory for reqid = " + reqid)	
		#elif (reqid,update,accNum,amount,client) not in completed and (reqid,update,accNum,amount,client) not in pending:	
		#	pending.append((reqid,update,accNum,amount,client))
		#	if update == 'Deposit':
		#		bank.deposit(accNum, amount)
		#	elif update == "Withdraw":
		#		result = bank.withdraw(accNum, amount)
		#		if result == False:
		#			outcome = Outcome.InsufficientFunds
		#			#output(self.name + "InsufficientFunds for reqid=" + reqid)
		#		#else:
		#			#output(self.name + "processed request for reqid=" + reqid)
		#	outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
		#	#output(self.name + "processed request for reqid=" + reqid)
		#else:
		#	(outcome, balance) = outcomeHistory[reqid]
		#	#outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
		#	pending.append((reqid,update,accNum,amount,client))
		#	send(('Reply',reqid,outcome,balance), to=client)
		#	send(('Ack',reqid, update, accNum, amount, outcome, client), to=predecessor)
		#	return
		#	#output(self.name + "duplicate request for reqid = " + reqid)
		#if self.role == 'tail':
		#	if outcome != Outcome.InconsistentWithHistory:
		#		outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
		#		completed.add((reqid,update,accNum,amount,client))
		#		pending.remove((reqid,update,accNum,amount,client))
		#		output(self.name + "sending ack to predecessor for reqid = " + reqid)
		#		send(('Ack',reqid, update, accNum, amount, outcome, client), to=predecessor)
		#	balance = bank.getBalance(accNum)
		#	output(self.name + "sending reply to client: (reqid = " + reqid + ", outcome= " + str(outcome) + ", balance = " + str(balance) + ")")
		#	send(('Reply',reqid,outcome,balance), to=client)
		#else:	
		#	output(self.name + "sending sync to successor for reqid = " + reqid)
		#	send(('Sync',reqid,update, accNum, amount, outcome, client), to=successor)

	def recv(msg=('Ack',reqid, update, accNum, amount, outcome, client), from_=source):
		output(self.name + "received Ack from successor for reqid = " + reqid)
		if (reqid, update, accNum, amount, client) in pending:
			pending.remove((reqid, update, accNum, amount, client))
		outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
		completed.append((reqid, update,accNum,amount,client))
		if self.role != 'head':
			output(self.name + "sending ack predecessor for reqid = " + reqid)
			output(self.name + "shashiiiiiii")
			if predecessor == None:
				output(self.name + "no predecessor")	
			send(('Ack',reqid, update, accNum, outcome, amount, client), to=predecessor)

class Bank(object):
        def __init__(self, bankname, master, chainextensionEnable, chainExtensionFailureMaxMsg, heartbeattimeoutserver):
                self.bankName = bankname
                self.accountList = []
                #self.servers = servers
                self.servers= []
                self.head = None
                self.tail = None
                self.heartbeattimeoutserver = heartbeattimeoutserver
                self.chainextensionEnable = chainextensionEnable
                self.chainExtensionFailureMaxMsg = chainExtensionFailureMaxMsg
                self.master = master
        def getServers(self):
                return self.servers
        def getBankName(self):
                return self.bankName
        def setServers(self, servers):
                self.servers = servers
                #if self.chainextensionEnable == 1:
                #       time.sleep(10)		
                #       self.startChainExtension()	
       
        def startChainExtension(self, maxrequest):
                servername = self.bankName + "::(server::extension(new tail)" + ")::"
                s = new(server)
                setup(s, [servername, None, None, None, self, self.master, maxrequest, 0, self.heartbeattimeoutserver])
                start(s)
 
        def setupServers(self, nservers, timeouts, heartbeattimeoutserver):
                #print(self.name+ "server timeout list size  " + str(len(timeouts)))
                #config(channel= 'fifo')
                self.servers = list(new(server, num = nservers))
                for j,s in enumerate(self.servers):
                        if j == 0:
                              #head
                              if len(self.servers) > 1:
                                    successor = self.servers[j+1]
                              else:
                                    successor = None
                              servername = self.bankName + "::head(server" + str(j+1) + ")::"
                              setup(s, [servername, 'head', successor, None, self, self.master, timeouts[j], 0, heartbeattimeoutserver])
                              self.head = s	
                        elif j == nservers - 1:
                              #tail
                              predecessor = self.servers[j-1]
                              servername = self.bankName + "::tail(server" + str(j+1) + ")::"
                              setup(s, [servername, 'tail', None, predecessor, self, self.master, timeouts[j], self.chainExtensionFailureMaxMsg, heartbeattimeoutserver])
                              self.tail = s
                        else:
                              #internal server
                              servername = self.bankName + "::server" + str(j+1) + "::"
                              successor = self.servers[j+1]
                              predecessor = self.servers[j-1]
                              #if j == 1:
                              #        setup(s, [servername, 'normal', successor, predecessor, self, self.master, 1])
                              #elif j == 2:
                              #        setup(s, [servername, 'normal', successor, predecessor, self, self.master, 2])
                              #else:
                              setup(s, [servername, 'normal', successor, predecessor, self, self.master, timeouts[j], 0, heartbeattimeoutserver])
                start(self.servers)
                if self.chainextensionEnable == 1:
                       #time.sleep(10)
                       maxrequest = timeouts[len(self.servers)]
                       self.startChainExtension(maxrequest) 
        def getHead(self):
                 return self.head
	
        def setHead(self, head):
                 self.head = head

        def setTail(self, tail):
                 self.tail = tail

        def getTail(self):
                 return self.tail

        def openAccount(self, accNum):
                acc = account(accNum)
                self.accountList.append(acc)
                return acc

        def getAccount(self, accNum):
                for account in self.accountList:
                        if account.accountNum == accNum:
                              return account
                return None

        def deposit(self, accNum, amount):
                account = self.getAccount(accNum)
                if account is None:
                        account = self.openAccount(accNum)
                account.deposit(amount)

        def withdraw(self, accNum, amount):
                account = self.getAccount(accNum)
                if account is None:
                        account = self.openAccount(accNum)
                return account.withdraw(amount)

        def getBalance(self, accNum):
                account = self.getAccount(accNum)
                if account is None:
                        account = self.openAccount(accNum)
                return account.getBalance()

class master(process):
	def setup(banks, bankclientmap, timeout):
		self.name = 'master::'
		self.banks = banks
		self.timeout = timeout
		self.rcvd = set()
		self.bankclientmap = bankclientmap
		self.chainExtensionReq = False
		self.newJoineeBank = None
		self.newJoinee = None
		#bankInfo
		self.bankInfo = {}
		
		output(self.name + " set up called...")
	def main():
		output(self.name + " main called...")
		for bank in banks:
			servers = bank.getServers()
			self.bankInfo[bank.getBankName()] = (bank.getHead(),bank.getTail())	
		for bank in banks:
			for s in servers:
				send(('bankdetail', self.bankInfo), to=s)		
		heartbeat()

	def heartbeat():
		#while self.heartbeatflag == True:
		while True:
			if await(False):
				pass
			elif timeout(timeout):
				output(self.name + "timeout called...")
				for bank in banks:
					servers = bank.getServers()
					output(self.name + "no of servers " + str(len(servers)))
					self.bankInfo[bank.getBankName()] = (bank.getHead(),bank.getTail())	
					for s in servers:
						if s not in rcvd:
							#output(self.name + "hearbeat not received")
							index = servers.index(s)
							if index == 0:
								newHead = servers[index+1]
								send(('rolechange','head'), to=newHead)
								clients = self.bankclientmap[bank]
								for c in clients:
									send(('newhead', newHead), to = c)
								bank.setHead(newHead)
							elif index == len(servers) - 1:
								# tail crashed make T- new tail
								output(self.name + " tail crashed.")
								newTail = servers[index-1]
								send(('rolechange','tail'), to=newTail)
								if self.chainExtensionReq == True and self.newJoinee != None:
									#new tail want to join
									send(('newjoinee',self.newJoinee), to=newTail)
									output(self.name + "taail failed sending new joinee to new tail ")
								clients = self.bankclientmap[bank]
								#output(self.name+ "client size " + str(len(clients)))
								for c in clients:
									send(('newtail', newTail), to = c)
								bank.setTail(newTail)
							else:
								#internal server crashed recover make tell S- about new successor and S+ about predecessor
								predecessor = servers[index-1]   #wrt crashed server
								successor = servers[index + 1]  #wrt crashed server
								send(('newsuccessor',successor), to=predecessor)
								send(('newpredecessor',predecessor), to=successor)
							servers.remove(s)
							bank.setServers(servers)
							output(self.name + "crashed server in chain index is " + str(index))

							#send(('terminate',), to=s)		
				self.rcvd = set()
				heartbeat()
	
	def recv(msg=('wanttojoin', bank), from_=source):
		self.chainExtensionReq = True
		#self.heartbeatflag = False
		self.newJoineeBank = bank
		self.newJoinee = source
		output(self.name + "wanttojoin request received...")
		oldtail = bank.getTail()	
		send(('newjoinee',source), to=oldtail)
		#if await(False):
		#	pass
		#elif timeout(timeout):
		#	self.chainExtensionReq = False
	
	def recv(msg=('synccomplete',bank), from_=source):	
		self.chainExtensionReq = False
		#send(('rolechange', 'tail'), to=source)
		for bank1 in banks:
			if bank.getBankName() == bank1.getBankName():
				servers = bank1.getServers()
				servers.append(source)
				bank1.setServers(servers)
				oldtail = bank1.getTail()
				bank1.setTail(source)
				clients = self.bankclientmap[bank1]
				for c in clients:
					send(('newtail', source), to = c)
		send(('rolechange','normal'), to=oldtail)
		send(('newsuccessor',source), to=oldtail)
		send(('rolechange', 'tail'), to=source)
		send(('newpredecessor',oldtail), to=source)
	
	def recv(msg=('heartbeat',), from_=source):
		output(self.name + "heartbeat received...")
		rcvd.add(source)
def main():
	name = "ChainReplication::"
	print(name + "reading config file...")
	seq=1
	parser = configparser.SafeConfigParser()
	parser.read('config.ini')
	numBank = int(parser.get('config_test', 'numBank'))
	banklist = []
	bankmaster = new(master)
	bankclientmap = {}
	bankInfo = {}	#information of banks head tail
	config(channel=('unfifo', 'unreliable'))	
	for t in range(1,numBank+1):
		bankname = parser.get('config_test', 'bank' + str(t))
		print(name + "bank name:" + bankname)
		heartbeattimeoutmaster = int(parser.get('config_test', 'heartbeattimeoutmaster'))
		heartbeattimeoutserver = int(parser.get('config_test', 'heartbeattimeoutserver'))
		chainextensionEnable = int(parser.get('config_test', 'bank' + str(t) + 'chainextensionenable'))
		#start servers and clients
		nclients = int(parser.get('config_test', 'bank' + str(t) + 'client'))
		clienttimeout = int(parser.get('config_test', 'bank' + str(t) + 'clienttimeout'))
		clientsleepafternumrequests = int(parser.get('config_test', 'bank' + str(t) + 'clientsleepafternumrequests'))
		clientignorerequests = int(parser.get('config_test', 'bank' + str(t) + 'clientignorerequests'))
		retransmittimeout = int(parser.get('config_test', 'bank' + str(t) + 'retransmitTimeout'))
		nservers = int(parser.get('config_test', 'bank' + str(t) + 'servers'))
		timeouts = []
		for index in range(1,nservers+1):
			timeout = int(parser.get('config_test', 'bank' + str(t) + 'server' + str(index) + 'maxrequests'))
			#print(name + 'timeoutssssssss   ' + str(timeout))
			timeouts.append(timeout)
		chainExtensionFailureMaxMsg = 0
		if chainextensionEnable == 1:
			timeout = int(parser.get('config_test', 'bank' + str(t) + 'extendedservermaxrequests'))
			chainExtensionFailureMaxMsg = int(parser.get('config_test', 'bank' + str(t) + 'server3chainextensionfailuremsgnum'))
			print(name + "chainExtensionFailureMaxMsg: " + str(chainExtensionFailureMaxMsg))
			timeouts.append(timeout)
		#config(channel=('unfifo', 'unreliable'))
		clients = list(new(client, num = nclients))
		bank = Bank(bankname, bankmaster, chainextensionEnable, chainExtensionFailureMaxMsg, heartbeattimeoutserver)
		banklist.append(bank)
		bank.setupServers(nservers,timeouts, heartbeattimeoutserver)

		#send auto requests
		bankreqType = parser.get('config_test', 'bank' + str(t) + 'reqType')
		for i in range(1,nclients+1):
			requestList = []
			if bankreqType == 'auto':
				numReq = int(parser.get('config_test', 'bank' + str(t) + 'numReq'))
				numGetBalance = float(parser.get('config_test', 'bank' + str(t) + 'probGetBalance')) * numReq
				numDeposit = float(parser.get('config_test', 'bank' + str(t) + 'probDeposit')) * numReq;
				numWithdraw = float(parser.get('config_test', 'bank' + str(t) + 'probWithdraw')) * numReq;
				accNo = random.randint(1, 100000)
				amt = random.randint(1, 1000)

				while numReq!=0:
					select = random.randint(1, 100000)%4 #change
					accNo = random.randint(1, 100000)
					amt = random.randint(1, 1000)
					if (numDeposit > 0 and select==0):
						reqId = parser.get('config_test', 'bank' + str(t) + 'client') + "." + "client" + str(i) + "." + str(seq)
						requestList.append((reqId, RequestType.Deposit,accNo, amt,None,None)) #change
						numDeposit = numDeposit-1
						numReq=numReq-1
						seq = seq+1
					if (numWithdraw > 0 and select==1):
						reqId = parser.get('config_test', 'bank' + str(t) + 'client') + "." + "client" + str(i) + "." + str(seq)
						requestList.append((reqId, RequestType.Withdraw,accNo, amt,None,None)) #change
						numWithdraw = numWithdraw-1
						numReq=numReq-1
						seq = seq+1
					if (numGetBalance > 0 and select==2):
						reqId = parser.get('config_test', 'bank' + str(t) + 'client') + "." + "client" + str(i) + "." + str(seq)
						requestList.append((reqId, RequestType.GetBalance,accNo,None,None,None)) #change
						numGetBalance = numGetBalance-1
						numReq=numReq-1
						seq = seq+1
					if (numGetBalance > 0 and select==3):
						bno=random.randint(1, numBank+1)
						dstAccNo = random.randint(1, 100000)
						if (bno!=t):
							bname = parser.get('config_test', 'bank' + str(bno))
							reqId = parser.get('config_test', 'bank' + str(t) + 'client') + "." + "client" + str(i) + "." + str(seq)
							requestList.append((reqId, RequestType.Transfer,accNo,bname,dstAccNo)) #change
							numGetBalance = numGetBalance-1
							numReq=numReq-1
							seq = seq+1
			elif bankreqType == 'itemized':
				bankitemisedReq = parser.get('config_test', 'bank' + str(t) + 'client' + str(i) + 'itemisedReq')
				reqlist = bankitemisedReq.split(';')
				
				for m in range(len(reqlist)):
					reqtype=reqlist[m].split(":")
					operation = reqtype[0]
					if operation == 'getBalance':
						reqargs = reqtype[1].split(",")
						#reqId = parser.get('config_test', 'bank' + str(t) + 'client') + "." + "client" + str(i) + "." + str(seq)
						requestList.append((reqargs[1], RequestType.GetBalance, int(reqargs[0]), None))
						#bank.getBalance(int(reqargs[0]))
						#seq = seq+1
					if operation == 'deposit':
						reqargs = reqtype[1].split(",")
						#reqId = parser.get('config_test', 'bank' + str(t) + 'client') + "." + "client" + str(i) + "." + str(seq)
						requestList.append((reqargs[2], RequestType.Deposit, int(reqargs[0]), int(reqargs[1])))
						#bank.deposit(int(reqargs[0]), int(reqargs[1]))
						#seq = seq+1
					if operation == 'withdraw':		
						reqargs = reqtype[1].split(",")
						#reqId = parser.get('config_test', 'bank' + str(t) + 'client') + "." + "client" + str(i) + "." + str(seq)
						#bank.deposit(int(reqargs[0]), int(reqargs[1]))
						requestList.append((reqargs[2], RequestType.Withdraw, int(reqargs[0]), int(reqargs[1])))
						#src for (rid, up, acc, am) in requests:
						#seq = seq+1
					#transfer
					if operation == 'transfer':		
						reqargs = reqtype[1].split(",")
						requestList.append((reqargs[2], RequestType.Transfer, int(reqargs[0]), int(reqargs[1]), reqargs[3], int(reqargs[4])))
					
					#outcomeHistory = {}
					#outcomeHistory[reqid] = (outcome,bank.getBalance(accNum))
			clientname = bankname + "::client" + str(i) + "::"
			c = clients[i-1]
			setup(c, [head, tail, requestList, clientname, clienttimeout, clientsleepafternumrequests, retransmittimeout, clientignorerequests])	
		start(clients)			
		bankclientmap[bank]=clients
	#end of banks
	#m = new(master)
	setup(bankmaster,[banklist, bankclientmap, heartbeattimeoutmaster])
	start(bankmaster)	

