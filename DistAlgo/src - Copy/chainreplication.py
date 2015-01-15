import da
PatternExpr_0 = da.pat.TuplePattern([da.pat.ConstantPattern('Reply'), da.pat.BoundPattern('_BoundPattern1_'), da.pat.BoundPattern('_BoundPattern2_'), da.pat.BoundPattern('_BoundPattern3_')])
PatternExpr_1 = da.pat.BoundPattern('_BoundPattern5_')
PatternExpr_3 = da.pat.TuplePattern([da.pat.ConstantPattern('newhead'), da.pat.BoundPattern('_BoundPattern18_')])
PatternExpr_5 = da.pat.TuplePattern([da.pat.ConstantPattern('newtail'), da.pat.BoundPattern('_BoundPattern30_')])
PatternExpr_7 = da.pat.TuplePattern([da.pat.ConstantPattern('newhead'), da.pat.FreePattern('head')])
PatternExpr_8 = da.pat.FreePattern('master')
PatternExpr_9 = da.pat.TuplePattern([da.pat.ConstantPattern('newtail'), da.pat.FreePattern('tail')])
PatternExpr_10 = da.pat.FreePattern('master')
PatternExpr_11 = da.pat.TuplePattern([da.pat.ConstantPattern('Reply'), da.pat.FreePattern('reqid'), da.pat.FreePattern('outcome'), da.pat.FreePattern('balance')])
PatternExpr_12 = da.pat.FreePattern('source')
PatternExpr_2 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.BoundPattern('_BoundPattern11_')]), da.pat.TuplePattern([da.pat.ConstantPattern('Reply'), da.pat.BoundPattern('_BoundPattern14_'), da.pat.BoundPattern('_BoundPattern15_'), da.pat.BoundPattern('_BoundPattern16_')])])
PatternExpr_4 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.ConstantPattern('newhead'), da.pat.BoundPattern('_BoundPattern28_')])])
PatternExpr_6 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.ConstantPattern('newtail'), da.pat.BoundPattern('_BoundPattern40_')])])
PatternExpr_13 = da.pat.TuplePattern([da.pat.ConstantPattern('terminate')])
PatternExpr_15 = da.pat.TuplePattern([da.pat.ConstantPattern('newsuccessor'), da.pat.BoundPattern('_BoundPattern66_')])
PatternExpr_17 = da.pat.TuplePattern([da.pat.ConstantPattern('newpredecessor'), da.pat.BoundPattern('_BoundPattern78_')])
PatternExpr_19 = da.pat.TuplePattern([da.pat.ConstantPattern('pendingtransactions'), da.pat.BoundPattern('_BoundPattern90_')])
PatternExpr_21 = da.pat.TuplePattern([da.pat.ConstantPattern('Ack'), da.pat.BoundPattern('_BoundPattern102_'), da.pat.BoundPattern('_BoundPattern103_'), da.pat.BoundPattern('_BoundPattern104_'), da.pat.BoundPattern('_BoundPattern105_'), da.pat.BoundPattern('_BoundPattern106_'), da.pat.BoundPattern('_BoundPattern107_')])
PatternExpr_23 = da.pat.TuplePattern([da.pat.BoundPattern('_BoundPattern123_'), da.pat.ConstantPattern('Query'), da.pat.BoundPattern('_BoundPattern125_')])
PatternExpr_25 = da.pat.TuplePattern([da.pat.BoundPattern('_BoundPattern137_'), da.pat.ConstantPattern('Deposit'), da.pat.BoundPattern('_BoundPattern139_'), da.pat.BoundPattern('_BoundPattern140_')])
PatternExpr_27 = da.pat.TuplePattern([da.pat.BoundPattern('_BoundPattern153_'), da.pat.ConstantPattern('Withdraw'), da.pat.BoundPattern('_BoundPattern155_'), da.pat.BoundPattern('_BoundPattern156_')])
PatternExpr_29 = da.pat.TuplePattern([da.pat.ConstantPattern('Sync'), da.pat.BoundPattern('_BoundPattern170_'), da.pat.BoundPattern('_BoundPattern171_'), da.pat.BoundPattern('_BoundPattern172_'), da.pat.BoundPattern('_BoundPattern173_'), da.pat.BoundPattern('_BoundPattern174_'), da.pat.BoundPattern('_BoundPattern175_')])
PatternExpr_31 = da.pat.TuplePattern([da.pat.ConstantPattern('newjoinee'), da.pat.FreePattern('newtail')])
PatternExpr_32 = da.pat.FreePattern('m')
PatternExpr_33 = da.pat.TuplePattern([da.pat.ConstantPattern('bankdetail'), da.pat.FreePattern('b'), da.pat.FreePattern('maxsize')])
PatternExpr_34 = da.pat.FreePattern('oldtail')
PatternExpr_35 = da.pat.TuplePattern([da.pat.ConstantPattern('newsuccessor'), da.pat.FreePattern('newsuccessor')])
PatternExpr_36 = da.pat.FreePattern('m')
PatternExpr_37 = da.pat.TuplePattern([da.pat.ConstantPattern('completedtransactions'), da.pat.FreePattern('rid'), da.pat.FreePattern('up'), da.pat.FreePattern('ac'), da.pat.FreePattern('am'), da.pat.FreePattern('cl'), da.pat.FreePattern('index'), da.pat.FreePattern('size')])
PatternExpr_38 = da.pat.FreePattern('source')
PatternExpr_39 = da.pat.TuplePattern([da.pat.ConstantPattern('pendingtransactions'), da.pat.FreePattern('pendingtrans')])
PatternExpr_40 = da.pat.FreePattern('m')
PatternExpr_43 = da.pat.TuplePattern([da.pat.ConstantPattern('newpredecessor'), da.pat.FreePattern('newpredecessor')])
PatternExpr_44 = da.pat.FreePattern('m')
PatternExpr_45 = da.pat.TuplePattern([da.pat.ConstantPattern('rolechange'), da.pat.FreePattern('role')])
PatternExpr_46 = da.pat.FreePattern('m')
PatternExpr_47 = da.pat.TuplePattern([da.pat.ConstantPattern('terminate')])
PatternExpr_48 = da.pat.FreePattern('m')
PatternExpr_49 = da.pat.TuplePattern([da.pat.FreePattern('reqid'), da.pat.FreePattern('update'), da.pat.FreePattern('accNum'), da.pat.FreePattern('amount')])
PatternExpr_50 = da.pat.FreePattern('client')
PatternExpr_53 = da.pat.TuplePattern([da.pat.ConstantPattern('Sync'), da.pat.FreePattern('reqid'), da.pat.FreePattern('update'), da.pat.FreePattern('accNum'), da.pat.FreePattern('amount'), da.pat.FreePattern('outcome'), da.pat.FreePattern('client')])
PatternExpr_54 = da.pat.FreePattern('source')
PatternExpr_55 = da.pat.TuplePattern([da.pat.ConstantPattern('Ack'), da.pat.FreePattern('reqid'), da.pat.FreePattern('update'), da.pat.FreePattern('accNum'), da.pat.FreePattern('amount'), da.pat.FreePattern('outcome'), da.pat.FreePattern('client')])
PatternExpr_56 = da.pat.FreePattern('source')
PatternExpr_14 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.ConstantPattern('terminate')])])
PatternExpr_16 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.ConstantPattern('newsuccessor'), da.pat.BoundPattern('_BoundPattern76_')])])
PatternExpr_18 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.ConstantPattern('newpredecessor'), da.pat.BoundPattern('_BoundPattern88_')])])
PatternExpr_20 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.ConstantPattern('pendingtransactions'), da.pat.BoundPattern('_BoundPattern100_')])])
PatternExpr_22 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.ConstantPattern('Ack'), da.pat.BoundPattern('_BoundPattern117_'), da.pat.BoundPattern('_BoundPattern118_'), da.pat.BoundPattern('_BoundPattern119_'), da.pat.BoundPattern('_BoundPattern120_'), da.pat.BoundPattern('_BoundPattern121_'), da.pat.BoundPattern('_BoundPattern122_')])])
PatternExpr_24 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.BoundPattern('_BoundPattern134_'), da.pat.ConstantPattern('Query'), da.pat.BoundPattern('_BoundPattern136_')])])
PatternExpr_26 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.BoundPattern('_BoundPattern149_'), da.pat.ConstantPattern('Deposit'), da.pat.BoundPattern('_BoundPattern151_'), da.pat.BoundPattern('_BoundPattern152_')])])
PatternExpr_28 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.BoundPattern('_BoundPattern165_'), da.pat.ConstantPattern('Withdraw'), da.pat.BoundPattern('_BoundPattern167_'), da.pat.BoundPattern('_BoundPattern168_')])])
PatternExpr_30 = da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.TuplePattern([da.pat.FreePattern(None), da.pat.FreePattern(None), da.pat.FreePattern(None)]), da.pat.TuplePattern([da.pat.ConstantPattern('Sync'), da.pat.BoundPattern('_BoundPattern185_'), da.pat.BoundPattern('_BoundPattern186_'), da.pat.BoundPattern('_BoundPattern187_'), da.pat.BoundPattern('_BoundPattern188_'), da.pat.BoundPattern('_BoundPattern189_'), da.pat.BoundPattern('_BoundPattern190_')])])
PatternExpr_57 = da.pat.TuplePattern([da.pat.ConstantPattern('wanttojoin'), da.pat.FreePattern('bank')])
PatternExpr_58 = da.pat.FreePattern('source')
PatternExpr_59 = da.pat.TuplePattern([da.pat.ConstantPattern('synccomplete'), da.pat.FreePattern('bank')])
PatternExpr_60 = da.pat.FreePattern('source')
PatternExpr_61 = da.pat.TuplePattern([da.pat.ConstantPattern('heartbeat')])
PatternExpr_62 = da.pat.FreePattern('source')
import sys
import configparser
import random
import time
from enum import Enum, unique
from threading import Thread


class account(object):

    def __init__(self, accNum=0, balance=0):
        self.accountNum = accNum
        self.balance = balance

    def getBalance(self):
        return self.balance

    def deposit(self, amount):
        self.balance = (self.balance + amount)

    def withdraw(self, amount):
        if (self.balance >= amount):
            self.balance = (self.balance - amount)
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


class client(da.DistProcess):

    def __init__(self, parent, initq, channel, props):
        super().__init__(parent, initq, channel, props)
        self._clientReceivedEvent_0 = []
        self._clientReceivedEvent_1 = []
        self._clientReceivedEvent_2 = []
        self._events.extend([
        da.pat.EventPattern(da.pat.ReceivedEvent, '_clientReceivedEvent_0', PatternExpr_0, sources=[PatternExpr_1], destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_clientReceivedEvent_1', PatternExpr_3, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_clientReceivedEvent_2', PatternExpr_5, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_clientReceivedEvent_3', PatternExpr_7, sources=[PatternExpr_8], destinations=None, timestamps=None, record_history=None, handlers=[self._client_handler_0]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_clientReceivedEvent_4', PatternExpr_9, sources=[PatternExpr_10], destinations=None, timestamps=None, record_history=None, handlers=[self._client_handler_1]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_clientReceivedEvent_5', PatternExpr_11, sources=[PatternExpr_12], destinations=None, timestamps=None, record_history=None, handlers=[self._client_handler_2])])

    def main(self):

        def announce():
            self.output('in cs ')
        super()._label('start', block=False)
        index = 0
        for (rid, up, acc, am) in self.requests:
            index = (index + 1)
            if (index == self.sleepafternumrequests):
                time.sleep(self.timeout)
            if (index > self.clientignorerequests):
                self.mutex(rid, up, acc, am, announce)
        _st_label_64 = 0
        self._timer_start()
        while (_st_label_64 == 0):
            _st_label_64+=1
            if PatternExpr_2.match_iter(self._clientReceivedEvent_0, _BoundPattern11_=self.tail, _BoundPattern14_=id, _BoundPattern15_=id, _BoundPattern16_=id):
                pass
                _st_label_64+=1
            elif self._timer_expired:
                for (rid, up, acc, am) in self.requests:
                    if (not (rid in self.recvd)):
                        self.output(((self.name + ' reply not received.') + rid))
                        self.mutex(rid, up, acc, am, announce)
                _st_label_64+=1
            else:
                super()._label('_st_label_64', block=True, timeout=self.retransmitTimeout)
                _st_label_64-=1
        super()._label('end', block=False)
        _st_label_70 = 0
        while (_st_label_70 == 0):
            _st_label_70+=1
            if (PatternExpr_4.match_iter(self._clientReceivedEvent_1, _BoundPattern28_=id) or PatternExpr_6.match_iter(self._clientReceivedEvent_2, _BoundPattern40_=id)):
                self.output('received reply')
                _st_label_70+=1
            else:
                super()._label('end', block=True)
                _st_label_70-=1

    def setup(self, head, tail, requests, name, timeout, sleepafternumrequests, retransmitTimeout, clientignorerequests):
        self.tail = tail
        self.timeout = timeout
        self.clientignorerequests = clientignorerequests
        self.requests = requests
        self.retransmitTimeout = retransmitTimeout
        self.name = name
        self.sleepafternumrequests = sleepafternumrequests
        self.head = head
        self.name = name
        self.head = head
        self.tail = tail
        self.requests = requests
        self.q = set()
        self.recvd = set()
        self.clientignorerequests = clientignorerequests
        self.sleepafternumrequests = sleepafternumrequests
        self.retransmitTimeout = retransmitTimeout
        self.output(((self.name + 'no of requests to be sent is... ') + str(len(requests))))
        self.timeout = timeout
        self.output((self.name + ' set up called...'))

    def mutex(self, rid, up, acc, am, task):
        super()._label('start', block=False)
        reqc = self.logical_clock()
        self.q.add((reqc, self.id, rid))
        if (up == RequestType.GetBalance):
            update = 'Query'
            self.output((((((((((self.name + ' sending request to bank (reqid= ') + rid) + ', operation=') + update) + ', accNum=') + str(acc)) + ', amount=') + str(am)) + ')'))
            self._send((rid, update, acc, am), self.tail)
        elif (up == RequestType.Deposit):
            update = 'Deposit'
            self.output((((((((((self.name + ' sending request to bank (reqid= ') + rid) + ', operation=') + update) + ', accNum=') + str(acc)) + ', amount=') + str(am)) + ')'))
            self._send((rid, update, acc, am), self.head)
        else:
            update = 'Withdraw'
            self.output((((((((((self.name + ' sending request to bank (reqid= ') + rid) + ', operation=') + update) + ', accNum=') + str(acc)) + ', amount=') + str(am)) + ')'))
            self._send((rid, update, acc, am), self.head)

    def _client_handler_0(self, master, head):
        self.output((self.name + 'new head'))
        self.head = head
    _client_handler_0._labels = None
    _client_handler_0._notlabels = None

    def _client_handler_1(self, tail, master):
        self.output((self.name + 'new tail'))
        self.tail = tail
    _client_handler_1._labels = None
    _client_handler_1._notlabels = None

    def _client_handler_2(self, reqid, balance, outcome, source):
        self.output(((((((self.name + 'reply from server: (reqid=') + reqid) + ', outcome=') + str(outcome)) + ',balance= ') + str(balance)))
        self.recvd.add(reqid)
    _client_handler_2._labels = None
    _client_handler_2._notlabels = None


class server(da.DistProcess):

    def __init__(self, parent, initq, channel, props):
        super().__init__(parent, initq, channel, props)
        self._serverReceivedEvent_0 = []
        self._serverReceivedEvent_1 = []
        self._serverReceivedEvent_2 = []
        self._serverReceivedEvent_3 = []
        self._serverReceivedEvent_4 = []
        self._serverReceivedEvent_5 = []
        self._serverReceivedEvent_6 = []
        self._serverReceivedEvent_7 = []
        self._serverReceivedEvent_8 = []
        self._events.extend([
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_0', PatternExpr_13, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_1', PatternExpr_15, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_2', PatternExpr_17, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_3', PatternExpr_19, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_4', PatternExpr_21, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_5', PatternExpr_23, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_6', PatternExpr_25, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_7', PatternExpr_27, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_8', PatternExpr_29, sources=None, destinations=None, timestamps=None, record_history=True, handlers=[]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_9', PatternExpr_31, sources=[PatternExpr_32], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_3]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_10', PatternExpr_33, sources=[PatternExpr_34], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_4]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_11', PatternExpr_35, sources=[PatternExpr_36], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_5]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_12', PatternExpr_37, sources=[PatternExpr_38], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_6]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_13', PatternExpr_39, sources=[PatternExpr_40], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_7]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_14', PatternExpr_43, sources=[PatternExpr_44], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_8]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_15', PatternExpr_45, sources=[PatternExpr_46], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_9]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_16', PatternExpr_47, sources=[PatternExpr_48], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_10]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_17', PatternExpr_49, sources=[PatternExpr_50], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_11]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_18', PatternExpr_53, sources=[PatternExpr_54], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_12]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_serverReceivedEvent_19', PatternExpr_55, sources=[PatternExpr_56], destinations=None, timestamps=None, record_history=None, handlers=[self._server_handler_13])])

    def main(self):
        super()._label('sync', block=False)
        _st_label_107 = 0
        while (_st_label_107 == 0):
            _st_label_107+=1
            if (PatternExpr_14.match_iter(self._serverReceivedEvent_0) or PatternExpr_16.match_iter(self._serverReceivedEvent_1, _BoundPattern76_=id) or PatternExpr_18.match_iter(self._serverReceivedEvent_2, _BoundPattern88_=id) or PatternExpr_20.match_iter(self._serverReceivedEvent_3, _BoundPattern100_=id)):
                pass
                _st_label_107+=1
            else:
                super()._label('sync', block=True)
                _st_label_107-=1
        _st_label_109 = 0
        while (_st_label_109 == 0):
            _st_label_109+=1
            if PatternExpr_22.match_iter(self._serverReceivedEvent_4, _BoundPattern117_=id, _BoundPattern118_=id, _BoundPattern119_=id, _BoundPattern120_=id, _BoundPattern121_=id, _BoundPattern122_=id):
                pass
                _st_label_109+=1
            else:
                super()._label('_st_label_109', block=True)
                _st_label_109-=1
        if (self.role == 'tail'):
            _st_label_112 = 0
            while (_st_label_112 == 0):
                _st_label_112+=1
                if PatternExpr_24.match_iter(self._serverReceivedEvent_5, _BoundPattern134_=id, _BoundPattern136_=id):
                    pass
                    _st_label_112+=1
                else:
                    super()._label('_st_label_112', block=True)
                    _st_label_112-=1
        elif (self.role == 'head'):
            _st_label_115 = 0
            while (_st_label_115 == 0):
                _st_label_115+=1
                if (PatternExpr_26.match_iter(self._serverReceivedEvent_6, _BoundPattern149_=id, _BoundPattern151_=id, _BoundPattern152_=id) or PatternExpr_28.match_iter(self._serverReceivedEvent_7, _BoundPattern165_=id, _BoundPattern167_=id, _BoundPattern168_=id)):
                    self.output((self.name + ':main received request from client'))
                    _st_label_115+=1
                else:
                    super()._label('_st_label_115', block=True)
                    _st_label_115-=1
        else:
            _st_label_117 = 0
            while (_st_label_117 == 0):
                _st_label_117+=1
                if PatternExpr_30.match_iter(self._serverReceivedEvent_8, _BoundPattern185_=id, _BoundPattern186_=id, _BoundPattern187_=id, _BoundPattern188_=id, _BoundPattern189_=id, _BoundPattern190_=id):
                    pass
                    _st_label_117+=1
                else:
                    super()._label('_st_label_117', block=True)
                    _st_label_117-=1

    def setup(self, name, role, successor, predecessor, bank, master, maxrequests, chainExtensionFailureMaxMsg, heartbeattimeoutserver):
        self.maxrequests = maxrequests
        self.predecessor = predecessor
        self.heartbeattimeoutserver = heartbeattimeoutserver
        self.master = master
        self.chainExtensionFailureMaxMsg = chainExtensionFailureMaxMsg
        self.successor = successor
        self.role = role
        self.name = name
        self.bank = bank
        if (role == None):
            time.sleep(5)
            self.output((self.name + "extended chain's new tail"))
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
        self.output(((self.name + 'self.chainExtensionFailureMaxMsg  ') + str(self.chainExtensionFailureMaxMsg)))
        self.maxrequests = maxrequests
        self.currentreqcount = 0
        self.output(((self.name + 'server set up called.. timeout ') + str(maxrequests)))
        if ((self.predecessor == None) and (self.successor == None)):
            self.output((self.name + 'new server in chain '))
            self.setupExtendedServer()
            self.sendheartbeat()
        else:
            self.sendheartbeat()

    def sendheartbeat(self):
        while True:
            _st_label_121 = 0
            self._timer_start()
            while (_st_label_121 == 0):
                _st_label_121+=1
                if False:
                    pass
                    _st_label_121+=1
                elif self._timer_expired:
                    if (self.currentreqcount < self.maxrequests):
                        self.output((self.name + 'heartbeat sending..'))
                        self._send(('heartbeat',), self.master)
                    _st_label_121+=1
                else:
                    super()._label('_st_label_121', block=True, timeout=self.heartbeattimeoutserver)
                    _st_label_121-=1
            else:
                if (_st_label_121 != 2):
                    continue
            if (_st_label_121 != 2):
                break

    def setupExtendedServer(self):
        self.output((self.name + 'sent wanttojoin to master'))
        self._send(('wanttojoin', self.bank), self.master)

    def handleQuery(self, reqid, query, accNum, junk, client):
        self.output((((((self.name + 'shashi::received query request from client: (reqid =') + reqid) + ', operation = Query, accNum =') + str(accNum)) + ')'))
        balance = self.bank.getBalance(accNum)
        self.output((((((((self.name + 'sending reply to client: (reqid = ') + reqid) + ', outcome= ') + str(Outcome.Processed)) + ', balance = ') + str(balance)) + ')'))
        self._send(('Reply', reqid, Outcome.Processed, balance), client)

    def handlerequest(self, reqid, update, accNum, amount, outcome, client):
        if (self.currentreqcount < self.maxrequests):
            self.currentreqcount = (self.currentreqcount + 1)
            self.output(((self.name + 'current request count is : ') + str(self.currentreqcount)))
        else:
            self.output((self.name + ' crashing server...'))
            return 
        self.output((((((((((self.name + 'received sync : (reqid= ') + reqid) + ', operation=') + update) + ', accNum=') + str(accNum)) + ', amount=') + str(amount)) + ')'))
        if (outcome == Outcome.InconsistentWithHistory):
            self.output(((self.name + 'InconsistentWithHistory for reqid = ') + reqid))
        elif ((not ((reqid, update, accNum, amount, client) in self.completed)) and (not ((reqid, update, accNum, amount, client) in self.pending))):
            self.pending.append((reqid, update, accNum, amount, client))
            if (update == 'Deposit'):
                self.bank.deposit(accNum, amount)
            elif (update == 'Withdraw'):
                result = self.bank.withdraw(accNum, amount)
                if (result == False):
                    outcome = Outcome.InsufficientFunds
            self.outcomeHistory[reqid] = (outcome, self.bank.getBalance(accNum))
        else:
            (outcome, balance) = self.outcomeHistory[reqid]
            self.pending.append((reqid, update, accNum, amount, client))
            self._send(('Reply', reqid, outcome, balance), client)
            self._send(('Ack', reqid, update, accNum, amount, outcome, client), self.predecessor)
            return 
        if (self.role == 'tail'):
            if (not (outcome == Outcome.InconsistentWithHistory)):
                self.outcomeHistory[reqid] = (outcome, self.bank.getBalance(accNum))
                self.completed.append((reqid, update, accNum, amount, client))
                self.pending.remove((reqid, update, accNum, amount, client))
                self.output(((self.name + 'sending ack to predecessor for reqid = ') + reqid))
                self._send(('Ack', reqid, update, accNum, amount, outcome, client), self.predecessor)
            balance = self.bank.getBalance(accNum)
            self.output((((((((self.name + 'sending reply to client: (reqid = ') + reqid) + ', outcome= ') + str(outcome)) + ', balance = ') + str(balance)) + ')'))
            self._send(('Reply', reqid, outcome, balance), client)
        else:
            self.output(((self.name + 'sending sync to successor for reqid = ') + reqid))
            self._send(('Sync', reqid, update, accNum, amount, outcome, client), self.successor)

    def _server_handler_3(self, newtail, m):
        self.output((self.name + 'new member joining request'))
        index = 0
        for (rid, up, ac, am, cl) in self.completed:
            size = len(self.completed)
            index = (index + 1)
            if ((index == self.chainExtensionFailureMaxMsg) and (self.chainExtensionFailureMaxMsg > 0)):
                self.currentreqcount = 100000
                self.output((self.name + ' crashed....'))
                if (self.currentreqcount > self.maxrequests):
                    return 
            self._send(('completedtransactions', rid, up, ac, am, cl, index, size), newtail)
            self.output(((self.name + 'index of current transacrion in completed list is ') + str(index)))
        self._send(('bankdetail', self.bank, 
        len(self.completed)), newtail)
        self.output(((((self.name + 'pending list size ') + str(len(self.pending))) + ' completed list size ') + str(len(self.completed))))
    _server_handler_3._labels = None
    _server_handler_3._notlabels = None

    def _server_handler_4(self, oldtail, maxsize, b):
        self.output(((((self.name + ' maxsize ') + str(maxsize)) + ' completed list size is ') + str(len(self.completed))))
        if (not (maxsize == len(self.completed))):
            self.output((self.name + 'bank detail not completed'))
            return 
        self.bank = b
        self._send(('synccomplete', self.bank), self.master)
    _server_handler_4._labels = None
    _server_handler_4._notlabels = None

    def _server_handler_5(self, m, newsuccessor):
        self.successor = newsuccessor
        self._send(('pendingtransactions', self.pending), newsuccessor)
    _server_handler_5._labels = None
    _server_handler_5._notlabels = None

    def _server_handler_6(self, source, am, cl, rid, index, up, size, ac):
        if (index == 0):
            self.completed = []
        if (self.currentreqcount < self.maxrequests):
            self.completed.append((rid, up, ac, am, cl))
            self.currentreqcount = (self.currentreqcount + 1)
            self.output(((self.name + 'current request count is : ') + str(self.currentreqcount)))
        else:
            self.output((self.name + 'crashing server'))
            return 
    _server_handler_6._labels = None
    _server_handler_6._notlabels = None

    def _server_handler_7(self, m, pendingtrans):
        self.output((self.name + 'received pending transation from predecesor'))
        for (rid, up, ac, am, cl) in pendingtrans:
            self.output(((self.name + 'received pending trans reqids: ') + rid))
        for (rid, up, ac, am, cl) in self.pending:
            self.output(((self.name + 'self pending trans reqids: ') + rid))
        for (rid, up, ac, am, cl) in self.completed:
            self.output(((self.name + 'self completed trans reqids: ') + rid))
        for (rid, up, ac, am, cl) in pendingtrans:
            account = update = reqid = client = amount = None

            def ExistentialOpExpr_0():
                nonlocal account, update, reqid, client, amount
                for (reqid, update, account, amount, client) in self.pending:
                    if ((rid == reqid) and ((not (up == update)) or (not (ac == account)) or (not (am == amount)) or (not (cl == client)))):
                        return True
                return False
            if ExistentialOpExpr_0():
                self.output(((self.name + 'inconsistent with history for reqid= ') + reqid))
                outcome = Outcome.InconsistentWithHistory
                self.handlerequest(rid, up, ac, am, outcome, cl)
            else:
                account = update = reqid = client = amount = None

                def ExistentialOpExpr_1():
                    nonlocal account, update, reqid, client, amount
                    for (reqid, update, account, amount, client) in self.completed:
                        if ((rid == reqid) and ((not (up == update)) or (not (ac == account)) or (not (am == amount)) or (not (cl == client)))):
                            return True
                    return False
                if ExistentialOpExpr_1():
                    self.output(((self.name + 'inconsistent with history for reqid = ') + reqid))
                    outcome = Outcome.InconsistentWithHistory
                    self.handlerequest(rid, up, ac, am, outcome, cl)
                elif ((not ((rid, up, ac, am, cl) in self.pending)) and (not ((rid, up, ac, am, cl) in self.completed))):
                    self.output((self.name + 'pendingtransactions3'))
                    outcome = Outcome.Processed
                    self.handlerequest(rid, up, ac, am, outcome, cl)
                elif (((rid, up, ac, am, cl) in self.pending) and (not ((rid, up, ac, am, cl) in self.completed))):
                    outcome = Outcome.Processed
                    self.handlerequest(rid, up, ac, am, outcome, cl)
                elif ((not ((rid, up, ac, am, cl) in self.pending)) and ((rid, up, ac, am, cl) in self.completed)):
                    (outcome, balance) = self.outcomeHistory[rid]
                    self.output(((self.name + 'sending ack to predecessor for reqid = ') + rid))
                    self._send(('Ack', rid, up, ac, am, outcome, cl), self.predecessor)
    _server_handler_7._labels = None
    _server_handler_7._notlabels = None

    def _server_handler_8(self, m, newpredecessor):
        self.predecessor = newpredecessor
    _server_handler_8._labels = None
    _server_handler_8._notlabels = None

    def _server_handler_9(self, role, m):
        self.role = role
        if (role == 'head'):
            self.output((self.name + 'new role:head'))
            self.predecessor = None
        elif (role == 'tail'):
            self.output((self.name + 'new role:tail'))
            self.successor = None
        elif (role == 'normal'):
            self.name = (self.name + '::oldtail')
            self.output((self.name + 'new role:normal server'))
    _server_handler_9._labels = None
    _server_handler_9._notlabels = None

    def _server_handler_10(self, m):
        self.output((self.name + 'terminate called..'))
    _server_handler_10._labels = None
    _server_handler_10._notlabels = None

    def _server_handler_11(self, amount, accNum, update, reqid, client):
        if (self.currentreqcount < self.maxrequests):
            self.currentreqcount = (self.currentreqcount + 1)
            self.output(((self.name + 'current request count is : ') + str(self.currentreqcount)))
        else:
            self.output((self.name + 'crashing server'))
            return 
        if (update == 'Query'):
            self.handleQuery(reqid, 'Query', accNum, amount, client)
            return 
        self.output((((((((((self.name + 'received request from client: (reqid= ') + reqid) + ', operation=') + update) + ', accNum=') + str(accNum)) + ', amount=') + str(amount)) + ')'))
        outcome = None
        if (((reqid, update, accNum, amount, client) in self.completed) or ((reqid, update, accNum, amount, client) in self.pending)):
            (outcome, balance) = self.outcomeHistory[reqid]
            self.pending.append((reqid, update, accNum, amount, client))
        else:
            ac = up = rid = cl = am = None

            def ExistentialOpExpr_2():
                nonlocal ac, up, rid, cl, am
                for (rid, up, ac, am, cl) in self.pending:
                    if ((rid == reqid) and ((not (up == update)) or (not (ac == account)) or (not (am == amount)) or (not (cl == client)))):
                        return True
                return False
            if ExistentialOpExpr_2():
                self.output(((self.name + 'inconsistent with history for reqid= ') + reqid))
                outcome = Outcome.InconsistentWithHistory
            else:
                ac = up = rid = cl = am = None

                def ExistentialOpExpr_3():
                    nonlocal ac, up, rid, cl, am
                    for (rid, up, ac, am, cl) in self.completed:
                        if ((rid == reqid) and ((not (up == update)) or (not (ac == account)) or (not (am == amount)) or (not (cl == client)))):
                            return True
                    return False
                if ExistentialOpExpr_3():
                    self.output(((self.name + 'inconsistent with history for reqid = ') + reqid))
                    outcome = Outcome.InconsistentWithHistory
                else:
                    self.pending.append((reqid, update, accNum, amount, client))
                    outcome = Outcome.Processed
                    if (update == 'Deposit'):
                        self.bank.deposit(accNum, amount)
                    elif (update == 'Withdraw'):
                        result = self.bank.withdraw(accNum, amount)
                        if (result == False):
                            outcome = Outcome.InsufficientFunds
                    self.outcomeHistory[reqid] = (outcome, self.bank.getBalance(accNum))
        if ((self.role == 'head') and (self.successor is None)):
            balance = self.bank.getBalance(accNum)
            if (not (outcome == Outcome.InconsistentWithHistory)):
                self.pending.remove((reqid, update, accNum, amount, client))
                self.completed.append((reqid, update, accNum, amount, client))
                self.outcomeHistory[reqid] = (outcome, self.bank.getBalance(accNum))
            self.output((((((((self.name + 'sending reply to client: (reqid = ') + reqid) + ', outcome= ') + str(outcome)) + ', balance = ') + str(balance)) + ')'))
            self._send(('Reply', reqid, outcome, balance), client)
        else:
            self.output(((self.name + 'sending sync to successor for reqid = ') + reqid))
            self._send(('Sync', reqid, update, accNum, amount, outcome, client), self.successor)
    _server_handler_11._labels = None
    _server_handler_11._notlabels = None

    def _server_handler_12(self, client, update, accNum, source, amount, outcome, reqid):
        self.handlerequest(reqid, update, accNum, amount, outcome, client)
    _server_handler_12._labels = None
    _server_handler_12._notlabels = None

    def _server_handler_13(self, amount, outcome, reqid, client, update, source, accNum):
        self.output(((self.name + 'received Ack from successor for reqid = ') + reqid))
        if ((reqid, update, accNum, amount, client) in self.pending):
            self.pending.remove((reqid, update, accNum, amount, client))
        self.outcomeHistory[reqid] = (outcome, self.bank.getBalance(accNum))
        self.completed.append((reqid, update, accNum, amount, client))
        if (not (self.role == 'head')):
            self.output(((self.name + 'sending ack predecessor for reqid = ') + reqid))
            self.output((self.name + 'shashiiiiiii'))
            if (self.predecessor == None):
                self.output((self.name + 'no predecessor'))
            self._send(('Ack', reqid, update, accNum, outcome, amount, client), self.predecessor)
    _server_handler_13._labels = None
    _server_handler_13._notlabels = None


class Bank(object):

    def __init__(self, bankname, master, chainextensionEnable, chainExtensionFailureMaxMsg, heartbeattimeoutserver):
        self.bankName = bankname
        self.accountList = []
        self.servers = []
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

    def startChainExtension(self, maxrequest):
        servername = ((self.bankName + '::(server::extension(new tail)') + ')::')
        s = da.api.new(server)
        da.api.setup(s, [servername, None, None, None, self, self.master, maxrequest, 0, self.heartbeattimeoutserver])
        da.api.start(s)

    def setupServers(self, nservers, timeouts, heartbeattimeoutserver):
        self.servers = list(da.api.new(server, num=nservers))
        for (j, s) in enumerate(self.servers):
            if (j == 0):
                if (len(self.servers) > 1):
                    successor = self.servers[(j + 1)]
                else:
                    successor = None
                servername = (((self.bankName + '::head(server') + str((j + 1))) + ')::')
                da.api.setup(s, [servername, 'head', successor, None, self, self.master, timeouts[j], 0, heartbeattimeoutserver])
                self.head = s
            elif (j == (nservers - 1)):
                predecessor = self.servers[(j - 1)]
                servername = (((self.bankName + '::tail(server') + str((j + 1))) + ')::')
                da.api.setup(s, [servername, 'tail', None, predecessor, self, self.master, timeouts[j], self.chainExtensionFailureMaxMsg, heartbeattimeoutserver])
                self.tail = s
            else:
                servername = (((self.bankName + '::server') + str((j + 1))) + '::')
                successor = self.servers[(j + 1)]
                predecessor = self.servers[(j - 1)]
                da.api.setup(s, [servername, 'normal', successor, predecessor, self, self.master, timeouts[j], 0, heartbeattimeoutserver])
        da.api.start(self.servers)
        if (self.chainextensionEnable == 1):
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
            if (account.accountNum == accNum):
                return account
        return None

    def deposit(self, accNum, amount):
        account = self.getAccount(accNum)
        if (account is None):
            account = self.openAccount(accNum)
        account.deposit(amount)

    def withdraw(self, accNum, amount):
        account = self.getAccount(accNum)
        if (account is None):
            account = self.openAccount(accNum)
        return account.withdraw(amount)

    def getBalance(self, accNum):
        account = self.getAccount(accNum)
        if (account is None):
            account = self.openAccount(accNum)
        return account.getBalance()


class master(da.DistProcess):

    def __init__(self, parent, initq, channel, props):
        super().__init__(parent, initq, channel, props)
        self._events.extend([
        da.pat.EventPattern(da.pat.ReceivedEvent, '_masterReceivedEvent_0', PatternExpr_57, sources=[PatternExpr_58], destinations=None, timestamps=None, record_history=None, handlers=[self._master_handler_14]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_masterReceivedEvent_1', PatternExpr_59, sources=[PatternExpr_60], destinations=None, timestamps=None, record_history=None, handlers=[self._master_handler_15]), 
        da.pat.EventPattern(da.pat.ReceivedEvent, '_masterReceivedEvent_2', PatternExpr_61, sources=[PatternExpr_62], destinations=None, timestamps=None, record_history=None, handlers=[self._master_handler_16])])

    def main(self):
        self.output((self.name + ' main called...'))
        self.heartbeat()

    def setup(self, banks, bankclientmap, timeout):
        self.bankclientmap = bankclientmap
        self.banks = banks
        self.timeout = timeout
        self.name = 'master::'
        self.banks = banks
        self.timeout = timeout
        self.rcvd = set()
        self.bankclientmap = bankclientmap
        self.chainExtensionReq = False
        self.newJoineeBank = None
        self.newJoinee = None
        self.output((self.name + ' set up called...'))

    def heartbeat(self):
        while True:
            _st_label_392 = 0
            self._timer_start()
            while (_st_label_392 == 0):
                _st_label_392+=1
                if False:
                    pass
                    _st_label_392+=1
                elif self._timer_expired:
                    self.output((self.name + 'timeout called...'))
                    for bank in self.banks:
                        servers = bank.getServers()
                        self.output(((self.name + 'no of servers ') + str(len(servers))))
                        for s in servers:
                            if (not (s in self.rcvd)):
                                index = servers.index(s)
                                if (index == 0):
                                    newHead = servers[(index + 1)]
                                    self._send(('rolechange', 'head'), newHead)
                                    clients = self.bankclientmap[bank]
                                    for c in clients:
                                        self._send(('newhead', newHead), c)
                                    bank.setHead(newHead)
                                elif (index == (len(servers) - 1)):
                                    self.output((self.name + ' tail crashed.'))
                                    newTail = servers[(index - 1)]
                                    self._send(('rolechange', 'tail'), newTail)
                                    if ((self.chainExtensionReq == True) and (not (self.newJoinee == None))):
                                        self._send(('newjoinee', self.newJoinee), newTail)
                                        self.output((self.name + 'taail failed sending new joinee to new tail '))
                                    clients = self.bankclientmap[bank]
                                    for c in clients:
                                        self._send(('newtail', newTail), c)
                                    bank.setTail(newTail)
                                else:
                                    predecessor = servers[(index - 1)]
                                    successor = servers[(index + 1)]
                                    self._send(('newsuccessor', successor), predecessor)
                                    self._send(('newpredecessor', predecessor), successor)
                                servers.remove(s)
                                bank.setServers(servers)
                                self.output(((self.name + 'crashed server in chain index is ') + str(index)))
                    self.rcvd = set()
                    self.heartbeat()
                    _st_label_392+=1
                else:
                    super()._label('_st_label_392', block=True, timeout=self.timeout)
                    _st_label_392-=1
            else:
                if (_st_label_392 != 2):
                    continue
            if (_st_label_392 != 2):
                break

    def _master_handler_14(self, bank, source):
        self.chainExtensionReq = True
        self.newJoineeBank = bank
        self.newJoinee = source
        self.output((self.name + 'wanttojoin request received...'))
        oldtail = bank.getTail()
        self._send(('newjoinee', source), oldtail)
    _master_handler_14._labels = None
    _master_handler_14._notlabels = None

    def _master_handler_15(self, bank, source):
        self.chainExtensionReq = False
        for bank1 in self.banks:
            if (bank.getBankName() == bank1.getBankName()):
                servers = bank1.getServers()
                servers.append(source)
                bank1.setServers(servers)
                oldtail = bank1.getTail()
                bank1.setTail(source)
                clients = self.bankclientmap[bank1]
                for c in clients:
                    self._send(('newtail', source), c)
        self._send(('rolechange', 'normal'), oldtail)
        self._send(('newsuccessor', source), oldtail)
        self._send(('rolechange', 'tail'), source)
        self._send(('newpredecessor', oldtail), source)
    _master_handler_15._labels = None
    _master_handler_15._notlabels = None

    def _master_handler_16(self, source):
        self.output((self.name + 'heartbeat received...'))
        self.rcvd.add(source)
    _master_handler_16._labels = None
    _master_handler_16._notlabels = None

def main():
    name = 'ChainReplication::'
    print((name + 'reading config file...'))
    seq = 1
    parser = configparser.SafeConfigParser()
    parser.read('config.ini')
    numBank = int(parser.get('config_test', 'numBank'))
    banklist = []
    bankmaster = da.api.new(master)
    bankclientmap = {}
    da.api.config(channel=('unfifo', 'unreliable'))
    for t in range(1, (numBank + 1)):
        bankname = parser.get('config_test', ('bank' + str(t)))
        print(((name + 'bank name:') + bankname))
        heartbeattimeoutmaster = int(parser.get('config_test', 'heartbeattimeoutmaster'))
        heartbeattimeoutserver = int(parser.get('config_test', 'heartbeattimeoutserver'))
        chainextensionEnable = int(parser.get('config_test', (('bank' + str(t)) + 'chainextensionenable')))
        nclients = int(parser.get('config_test', (('bank' + str(t)) + 'client')))
        clienttimeout = int(parser.get('config_test', (('bank' + str(t)) + 'clienttimeout')))
        clientsleepafternumrequests = int(parser.get('config_test', (('bank' + str(t)) + 'clientsleepafternumrequests')))
        clientignorerequests = int(parser.get('config_test', (('bank' + str(t)) + 'clientignorerequests')))
        retransmittimeout = int(parser.get('config_test', (('bank' + str(t)) + 'retransmitTimeout')))
        nservers = int(parser.get('config_test', (('bank' + str(t)) + 'servers')))
        timeouts = []
        for index in range(1, (nservers + 1)):
            timeout = int(parser.get('config_test', (((('bank' + str(t)) + 'server') + str(index)) + 'maxrequests')))
            timeouts.append(timeout)
        chainExtensionFailureMaxMsg = 0
        if (chainextensionEnable == 1):
            timeout = int(parser.get('config_test', (('bank' + str(t)) + 'extendedservermaxrequests')))
            chainExtensionFailureMaxMsg = int(parser.get('config_test', (('bank' + str(t)) + 'server3chainextensionfailuremsgnum')))
            print(((name + 'chainExtensionFailureMaxMsg: ') + str(chainExtensionFailureMaxMsg)))
            timeouts.append(timeout)
        clients = list(da.api.new(client, num=nclients))
        bank = Bank(bankname, bankmaster, chainextensionEnable, chainExtensionFailureMaxMsg, heartbeattimeoutserver)
        banklist.append(bank)
        bank.setupServers(nservers, timeouts, heartbeattimeoutserver)
        head = bank.getHead()
        tail = bank.getTail()
        bankreqType = parser.get('config_test', (('bank' + str(t)) + 'reqType'))
        for i in range(1, (nclients + 1)):
            requestList = []
            if (bankreqType == 'auto'):
                numReq = int(parser.get('config_test', (('bank' + str(t)) + 'numReq')))
                numGetBalance = (float(parser.get('config_test', (('bank' + str(t)) + 'probGetBalance'))) * numReq)
                numDeposit = (float(parser.get('config_test', (('bank' + str(t)) + 'probDeposit'))) * numReq)
                numWithdraw = (float(parser.get('config_test', (('bank' + str(t)) + 'probWithdraw'))) * numReq)
                accNo = random.randint(1, 100000)
                amt = random.randint(1, 1000)
                while (not (numReq == 0)):
                    select = (random.randint(1, 100000) % 3)
                    accNo = random.randint(1, 100000)
                    amt = random.randint(1, 1000)
                    if ((numDeposit > 0) and (select == 0)):
                        reqId = (((((parser.get('config_test', (('bank' + str(t)) + 'client')) + '.') + 'client') + str(i)) + '.') + str(seq))
                        requestList.append((reqId, RequestType.Deposit, accNo, amt))
                        numDeposit = (numDeposit - 1)
                        numReq = (numReq - 1)
                        seq = (seq + 1)
                    if ((numWithdraw > 0) and (select == 1)):
                        reqId = (((((parser.get('config_test', (('bank' + str(t)) + 'client')) + '.') + 'client') + str(i)) + '.') + str(seq))
                        requestList.append((reqId, RequestType.Withdraw, accNo, amt))
                        numWithdraw = (numWithdraw - 1)
                        numReq = (numReq - 1)
                        seq = (seq + 1)
                    if ((numGetBalance > 0) and (select == 2)):
                        reqId = (((((parser.get('config_test', (('bank' + str(t)) + 'client')) + '.') + 'client') + str(i)) + '.') + str(seq))
                        requestList.append((reqId, RequestType.GetBalance, accNo, None))
                        numGetBalance = (numGetBalance - 1)
                        numReq = (numReq - 1)
                        seq = (seq + 1)
            elif (bankreqType == 'itemized'):
                bankitemisedReq = parser.get('config_test', (((('bank' + str(t)) + 'client') + str(i)) + 'itemisedReq'))
                reqlist = bankitemisedReq.split(';')
                for m in range(len(reqlist)):
                    reqtype = reqlist[m].split(':')
                    operation = reqtype[0]
                    if (operation == 'getBalance'):
                        reqargs = reqtype[1].split(',')
                        requestList.append((reqargs[1], RequestType.GetBalance, 
                        int(reqargs[0]), None))
                    if (operation == 'deposit'):
                        reqargs = reqtype[1].split(',')
                        requestList.append((reqargs[2], RequestType.Deposit, 
                        int(reqargs[0]), 
                        int(reqargs[1])))
                    if (operation == 'withdraw'):
                        reqargs = reqtype[1].split(',')
                        requestList.append((reqargs[2], RequestType.Withdraw, 
                        int(reqargs[0]), 
                        int(reqargs[1])))
            clientname = (((bankname + '::client') + str(i)) + '::')
            c = clients[(i - 1)]
            da.api.setup(c, [head, tail, requestList, clientname, clienttimeout, clientsleepafternumrequests, retransmittimeout, clientignorerequests])
        da.api.start(clients)
        bankclientmap[bank] = clients
    da.api.setup(bankmaster, [banklist, bankclientmap, heartbeattimeoutmaster])
    da.api.start(bankmaster)
